package org.core;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
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

  // success and failure checks for each thread
  public int success = 0;
  public int failure = 0;

  //histogram implementation
  int[] histogramGET = new int[500];
  int[] histogramPOST = new int[500];
  int[] histogramALL = new int[500];
  int over_5s;

  public void updateGET(int millis) {
    if (millis >= 5000) {
      over_5s++;
    } else {
      histogramGET[millis/10]++;
      histogramALL[millis/10]++;
    }
  }
  public void updatePOST(int millis) {
    if (millis >= 5000) {
      over_5s++;
    } else {
      histogramPOST[millis/10]++;
      histogramALL[millis/10]++;
    }
  }

  public int findAvg(int[] histogram) {
    int sum = 0;
    for (int i : histogram) {
      sum += i;
    }
    return sum/histogram.length;
  }

  public Client() {}

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
    final Timestamp[] timestamp = {new Timestamp(System.currentTimeMillis())};

    long startupTime = timestamp[0].getTime();

    //Creating the 5 second logging
    Client finalClient = client;
    final Runnable print5Seconds = () -> System.out.println("Tasks completed in the last 5 seconds: "
        + finalClient.getSuccess() + finalClient.getFailure());

    ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(2);
    final ScheduledFuture<?> printoutHandler = scheduler.scheduleAtFixedRate(print5Seconds,5, 5, TimeUnit.SECONDS);
    scheduler.schedule(new Runnable() {
      @Override
      public void run() {
        printoutHandler.cancel(true);
        //Capture total time spent on operation before shutdown
        timestamp[0] = new Timestamp(System.currentTimeMillis());
        long afterPhase3 = timestamp[0].getTime();

        String output = String
            .format("NumThreads = %d, numSkiers = %d, numLifts = %d", finalClient.numThreads,
                finalClient.numSkiers, finalClient.numLifts);
        logger.info("Configuration - " + output);
        logger.info("Success " + finalClient.success);
        logger.info("failure " + finalClient.failure);
        logger.info("average request length for POST" + finalClient.findAvg(finalClient.histogramPOST));
        logger.info("average request length for GET" + finalClient.findAvg(finalClient.histogramGET));
        logger.info("total time " + (afterPhase3 - startupTime) / 1000.0 + " seconds.");

        System.exit(0);
      }
      //shutdown after 15 minutes
    }, 60 * 15, TimeUnit.SECONDS);

    //creation of the threads
    for (int i = 0; i < client.numThreads / 4; i++) {
      Thread threadPhase1 = new Thread(client, skiersPerThread, random,
          1, 1,
          420);
      java.lang.Thread thread1 = new java.lang.Thread(threadPhase1);
      thread1.start();
    }



  }
}
