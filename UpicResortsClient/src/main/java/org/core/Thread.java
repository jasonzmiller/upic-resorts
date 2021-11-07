package org.core;

import java.io.UnsupportedEncodingException;
import java.util.Base64;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Random;

public class Thread implements Runnable {

  private static final Logger logger = LogManager.getLogger(Thread.class);
  private static final String URL_CORE = "/skiers/%resort_id%/seasons/2021/days/22/skiers/";
  private Client client;
  private int skiersPerThread;
  private Random random;
  private double coefficient;
  private int startTime;
  private int endTime;
  private int phase;
  private HttpClient httpClient;

  public Thread(Client client, int skiersPerThread, Random random, double coefficient,
      int startTime, int endTime, int phase) {
    this.client = client;
    this.skiersPerThread = skiersPerThread;
    this.random = random;
    this.coefficient = coefficient;
    this.startTime = startTime;
    this.endTime = endTime;
    this.phase = phase;
    httpClient = HttpClient.newHttpClient();
  }

  @Override
  public void run() {
    try {
      for (int j = 0; j < this.client.numRuns * coefficient * skiersPerThread; j++) {
        // Code takes too long to run, reduce the number of requests in half
        if (j % 3 == 0 || j % 3 == 1)
        {
          client.testSuccess();
          continue;
        }
        //creating random skier id for EACH thread
        int skierId = random.nextInt(this.client.numSkiers) + 1;

        //choosing a random liftID
        int liftId = random.nextInt(this.client.numLifts);
        // 40 lifts and 5 resorts, so 8 for each resort.
        int resortId = getResortId(liftId);
        liftId = (liftId % 8) + 1;
        //choosing random time period
        int time = startTime + random.nextInt(endTime - startTime + 1);

        try {
          String url = Client.IP + URL_CORE + skierId;
          url = url.replace("%resort_id%", "" + resortId);
          String messageBody = String.format("{\"liftId\" : %d, \"time\" : %d}", liftId, time);
          //logger.debug("Calling url " + url + "\n with body - " + messageBody);
          HttpResponse<String> res = doPost(httpClient, url, messageBody);
          if (res.statusCode() == 201) {
            client.testSuccess();
          } else if (res.statusCode() >= 400) {
            client.testFailure();
            logger.error("Failure: " + client.getFailure() + "\n caused by :" + res.body());
          }

          // make a get request immediately
          HttpResponse<String> getRes = doGet(httpClient, url);
          if (res.statusCode() >= 400) {
            logger.error("Failure: GET request " + client.getFailure() + "\n caused by :" + getRes.body() + " url - " + url);
          }

        } catch (Exception e) {
          client.testFailure();
          logger.error("Failure: " + client.getFailure());
          logger.error("Failed due to ", e);
        }
      }
    } finally {
      // depending on the phase, lower the countDownLatch
      if (phase == 1) {
        client.phase1.countDown();
      } else if (phase == 2) {
        client.phase2.countDown();
      }
      client.total.countDown();
    }
  }

  private int getResortId(int liftId) {
    return (liftId / 8) + 1;
  }

  private static HttpResponse<String> doGet(HttpClient client, String URL)
      throws IOException, InterruptedException {
    HttpRequest req = HttpRequest.newBuilder()
        .header("accept", "application/json")
        .uri(URI.create(URL))
        .build();
    return client.send(req, HttpResponse.BodyHandlers.ofString());
  }

  private static HttpResponse<String> doPost(HttpClient client, String URL, String body)
      throws IOException, InterruptedException {
    HttpRequest req = HttpRequest.newBuilder()
        .header("Content-Type", "application/json")
        .header("Authorization", getAuthHeaderString())
        .POST(HttpRequest.BodyPublishers.ofString(body))
        .uri(URI.create(URL))
        .build();
    return client.send(req, HttpResponse.BodyHandlers.ofString());
  }

  private static String getAuthHeaderString() {
    try {
      String credential = Base64.getEncoder().encodeToString(("admin:admin").getBytes("UTF-8"));
      return "Basic " + credential.substring(0, credential.length()-1);
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }
    return "";
  }
}
