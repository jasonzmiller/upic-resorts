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
  private static final String URL_CORE = "/liftrides/";
  private Client client;
  private Random random;
  private HttpClient httpClient;

  public Thread(Client client) {
    this.client = client;
    this.random = new Random();

    httpClient = HttpClient.newHttpClient();
  }

  @Override
  public void run() {
    while (!client.isDone())
    {
      int skierId = random.nextInt(50) + 1;
      int resortId = random.nextInt(4) + 1;
      int liftId = random.nextInt(8) + 1;
      int time = random.nextInt(500) + 1;
      String url = Client.IP + URL_CORE;
      String messageBody = String.format("{\"liftId\" : %d, \"time\" : %d, \"resortId\" : %d, \"skierId\" : %d}", liftId, time, resortId, skierId);
      try {

        int startTimePost = (int) System.currentTimeMillis();
        HttpResponse<String> res = doPost(httpClient, url, messageBody);
        client.updatePOST((int) System.currentTimeMillis() - startTimePost);

        if (res.statusCode() == 201) {
          client.testSuccess();
        } else if (res.statusCode() >= 400) {
          client.testFailure();
          logger.error("Failure: " + client.getFailure() + "\n caused by :" + res.body());
        }

        int startTimeGet = (int) System.currentTimeMillis();
        HttpResponse<String> getRes = doGet(httpClient, url);
        client.updateGET((int) System.currentTimeMillis() - startTimeGet);
        if (getRes.statusCode() >= 400) {
          logger.error("Failure: GET request " + client.getFailure() + "\n caused by :" + getRes.body() + " url - " + url);
        }
      } catch (Exception e) {
        client.testFailure();
        logger.error("Failure: " + client.getFailure());
        logger.error("Failed due to ", e);
      }
    }
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
