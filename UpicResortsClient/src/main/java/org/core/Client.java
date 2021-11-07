package org.core;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;

import java.sql.Timestamp;
import java.util.Random;
import java.util.concurrent.CountDownLatch;

public class Client {

  private static final Logger logger = LogManager.getLogger(Client.class);
  // parameters ideally in env, but here for testing
  public int numThreads = 32;
  public int numSkiers = 10000;
  public int numLifts = 40;
  public int numRuns = 20;

  public static String IP = "http://3.15.180.159:8080/UpicResortsServer-2.5.6";

  //phase 2 starts after 10% of part 1 is finished
  public final CountDownLatch phase1;
  //phase 3 starts after 10% of total threads finished
  public final CountDownLatch phase2;
  public final CountDownLatch total;

  // success and failure checks for each thread
  public int success = 0;
  public int failure = 0;

  public Client() {
    //phase 2 starts after 10% of part 1 is finished
    phase1 = new CountDownLatch(numThreads / 4 / 10);

    phase2 = new CountDownLatch(numThreads / 10);

    total = new CountDownLatch(numThreads / 4 + numThreads + numThreads / 4);
  }

  public Client(int numThreads, int numSkiers, int numLifts, int numRuns, String ip) {
    if (numThreads < 0 || numThreads > 256) {
      throw new IllegalArgumentException("Numthreads should be between 0 and 256");
    }
    if (numSkiers < 0 || numSkiers > 50000) {
      throw new IllegalArgumentException("NumSkiers should be between 0 and 50000");
    }
    if (numLifts < 5 || numLifts > 60) {
      throw new IllegalArgumentException("NumLifts should be between 5 and 60");
    }
    if (numRuns < 0 || numRuns > 20) {
      throw new IllegalArgumentException("NumRuns should be between 1 and 20");
    }
    this.numThreads = numThreads;
    this.numSkiers = numSkiers;
    this.numLifts = numLifts;
    this.numRuns = numRuns;
    this.IP = ip;
    String output = String
        .format("NumThreads = %d, numSkiers = %d, numLifts = %d", this.numThreads,
            this.numSkiers, this.numLifts);
    logger.info("Initialized with " + output);
    //phase 2 starts after 10% of part 1 is finished
    phase1 = new CountDownLatch(numThreads / 4 / 10);

    phase2 = new CountDownLatch(numThreads / 10);

    total = new CountDownLatch(
        numThreads / 4 + numThreads + numThreads / 4);
  }

  public synchronized void testSuccess() {
    success++;
  }

  public synchronized void testFailure() {
    failure++;
  }

  public synchronized int getSuccess() {
    return success;
  }

  public synchronized int getFailure() {
    return failure;
  }


  public static void main(String[] args) throws InterruptedException {
    Logger.getRootLogger()
        .addAppender(new ConsoleAppender(new PatternLayout(PatternLayout.TTCC_CONVERSION_PATTERN)));
    Client client = null;
    if (args.length == 5) {
      client = new Client(
          Integer.parseInt(args[0]),
          Integer.parseInt(args[1]),
          Integer.parseInt(args[2]),
          Integer.parseInt(args[3]),
          args[4]);
    }
    else
    {
      System.out.println("invalid params ");
      for (String arg : args){
        System.out.println(arg);
      }
      System.exit(0);
    }
    final int skiersPerThread = client.numSkiers / client.numThreads;
    final Random random = new Random();
    Timestamp timestamp = new Timestamp(System.currentTimeMillis());

    long startupTime = timestamp.getTime();


        /*Phase 1, the startup phase, will launch numThreads/4 threads, and each thread will be passed:
        > a start and end range for skierIDs, so that each thread has an identical number of skierIDs,
        calculated as numSkiers/(numThreads/4). Pass each thread a disjoint range of skierIDs so that
        the whole range of IDs is covered by the threads, ie, thread 0 has skierIDs from
        1 to (numSkiers/(numThreads/4)), thread 1 has skierIDs from (1x(numSkiers/(numThreads/4)+1)
        to (numSkiers/(numThreads/4))x2
        > a start and end time, for this phase this is the first 90 minutes of the ski day (1-90)
        For example if numThreads=64 and numSkiers=1024, we will launch 16 threads, with thread 0
        passed skierID range 1 to 64, thread 1 range 65 to 128, and so on.*/

    for (int i = 0; i < client.numThreads / 4; i++) {
      Thread threadPhase1 = new Thread(client, client.numSkiers / (client.numThreads / 4), random,
          0.1, 1,
          90, 1);
      java.lang.Thread thread1 = new java.lang.Thread(threadPhase1);
      thread1.start();
    }

    logger.info("Waiting for phase 1 to complete...");
    client.phase1.await();


        /*Once 10% (rounded up) of the threads in Phase 1 have completed, Phase 2, the peak phase should begin. Phase 2 behaves like Phase 1, except:
        > it creates numThreads threads
        > the start and end time interval is 91 to 360
        > each thread is passed a disjoint skierID range of size (numSkiers/numThreads)
        As above, each thread will randomly select a skierID, liftID and time from the ranges provided and
        sends a POST request. It will do this (numRuns x 0.8)x(numSkiers/numThreads) times. Back to our
        example above, this means phase 2 would create 64 threads, and and each sends 16*(1024/64) POSTs.*/

    for (int i = 0; i < client.numThreads; i++) {
      Thread threadPhase2 = new Thread(client, skiersPerThread, random, 0.8, 91, 360, 2);
      java.lang.Thread thread2 = new java.lang.Thread(threadPhase2);
      thread2.start();
    }

    logger.info("Waiting for phase 2 to complete...");
    client.phase2.await();


        /*Finally, once 10% of the threads in Phase 2 complete, Phase 3 should begin. Phase 3, the
        cooldown phase, is identical to Phase 1, starting 25% of numThreads, with each thread sending
        (0.1 x numRuns) POST requests, and with a time interval range of 361 to 420.*/

    for (int i = 0; i < client.numThreads / 4; i++) {
      Thread threadPhase3 = new Thread(client, client.numSkiers / (client.numThreads / 4), random,
          0.1,
          361, 420, 3);
      java.lang.Thread thread3 = new java.lang.Thread(threadPhase3);
      thread3.start();
    }
    logger.info("Waiting for all phases to complete...");
    client.total.await();

    //Capture total time spent on operation
    timestamp = new Timestamp(System.currentTimeMillis());
    long afterPhase3 = timestamp.getTime();

    String output = String
        .format("NumThreads = %d, numSkiers = %d, numLifts = %d", client.numThreads,
            client.numSkiers, client.numLifts);
    logger.info("Configuration - " + output);
    logger.info("Success " + client.success);
    logger.info("failure " + client.failure);
    logger.info("total time " + (afterPhase3 - startupTime) / 1000.0 + " seconds.");
  }
}
