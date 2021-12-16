package org.core;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
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

  //public static String IP = "http://3.15.180.159:8080/UpicResortsServer-2.5.6";
  public static String IP = "http://localhost:8080";
  // success and failure checks for each thread
  public int success = 0;
  public int failure = 0;

  private boolean done = false;
  //histogram implementation
  int[] histogramGET = new int[500];
  int[] histogramPOST = new int[500];
  int[] histogramALL = new int[500];
  int over_5s;

  public boolean isDone() {
    return done;
  }

  public void updateGET(int millis) {
    if (millis >= 5000) {
      over_5s++;
    } else {
      histogramGET[millis / 10]++;
      histogramALL[millis / 10]++;
    }
  }

  public synchronized void updatePOST(int millis) {
    if (millis >= 5000) {
      over_5s++;
    } else {
      histogramPOST[millis / 10]++;
      histogramALL[millis / 10]++;
    }
  }

  public double findAvg(int[] histogram) {
    int sum = 0;
    for (int i : histogram) {
      sum += i;
    }
    return (sum * 1.0 / histogram.length);
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

  public Client(int numThreads) {
    this.numThreads = numThreads;
  }

  public static void main(String[] args) throws InterruptedException {
    Logger.getRootLogger()
        .addAppender(new ConsoleAppender(new PatternLayout(PatternLayout.TTCC_CONVERSION_PATTERN)));
    int TIME_TO_RUN = 1; // in minutes
    long startupTime = System.currentTimeMillis();
    //Creating the 5 second logging
    Client client = new Client(3);
    final Runnable print5Seconds = () -> {
      System.out
          .print("Tasks completed in the last 5 seconds: "
              + (client.getSuccess() + client.getFailure()));
      System.out.println(
          " Elapsed time -" + (System.currentTimeMillis() - startupTime) / 1000.0 + " seconds.");
    };
    final Runnable setFlag = () -> {
      System.out.println("setting flag");
      client.done = true;
    };
    ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(3);
    scheduler.schedule(setFlag, TIME_TO_RUN * 60, TimeUnit.SECONDS);
    final ScheduledFuture<?> printoutHandler = scheduler
        .scheduleAtFixedRate(print5Seconds, 5, 5, TimeUnit.SECONDS);
    scheduler.schedule(new Runnable() {
      @Override
      public void run() {
        printoutHandler.cancel(true);
        //Capture total time spent on operation before shutdown
        long afterPhase3 = System.currentTimeMillis();

        String output = String
            .format("NumThreads = %d", client.numThreads);
        logger.info("Configuration - " + output);
        logger.info("Success " + client.success);
        logger.info("failure " + client.failure);
        logger.info("average request length for POST " + client.findAvg(client.histogramPOST));
        logger.info("average request length for GET " + client.findAvg(client.histogramGET));
        logger.info("total time " + (afterPhase3 - startupTime) / 1000.0 + " seconds.");

        System.exit(0);
      }
      //shutdown after 15 minutes
    }, 60 * TIME_TO_RUN, TimeUnit.SECONDS);

    //creation of the threads
    for (int i = 0; i < client.numThreads; i++) {
      Thread threadPhase1 = new Thread(client);
      java.lang.Thread thread1 = new java.lang.Thread(threadPhase1);
      thread1.start();
    }


  }
}
