package org.core;

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
    private static final String URL_CORE = "/skier-server/skiers/1/seasons/2018/days/22/skiers/";
    private Client client;
    private int skiersPerThread;
    private Random random;
    private double coefficient;
    private int startTime;
    private int endTime;
    private int phase;
    private HttpClient httpClient;

    public Thread(Client client, int skiersPerThread, Random random, double coefficient, int startTime, int endTime, int phase) {
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
                //creating random skier id for EACH thread
                int skierId = random.nextInt(this.client.numSkiers) + j * this.skiersPerThread;

                //choosing a random liftID
                int liftId = random.nextInt(this.client.numLifts);

                //choosing random time period
                int time = startTime + random.nextInt(endTime - startTime + 1);

                try {
                    HttpResponse<String> res = doPost(httpClient, Client.IP + URL_CORE + skierId, String.format("{'liftId' : %d, 'time' : %d}", liftId, time));
                    if (res.statusCode() == 201)
                    {
                        client.testSuccess();
                    }
                    else if (res.statusCode() >= 400 )
                    {
                        client.testFailure();
                        logger.error("Failure: " + client.getFailure() + "\n caused by :" + res.body());
                    }


                } catch (Exception e) {
                    client.testFailure();
                    logger.error("Failure: " + client.getFailure());
                    logger.error("Failed due to ", e);
                }
            }
        } finally {
            // depending on the phase, lower the countDownLatch
            if(phase == 1) {
                client.phase1.countDown();
            } else if (phase == 2) {
                client.phase2.countDown();
            }
            client.total.countDown();
        }
    }

    private static HttpResponse<String> doGet(HttpClient client, String URL) throws IOException, InterruptedException {
        HttpRequest req = HttpRequest.newBuilder()
                .header("accept","application/json")
                .uri(URI.create(URL))
                .build();
        return client.send(req, HttpResponse.BodyHandlers.ofString());
    }

    private static HttpResponse<String> doPost(HttpClient client, String URL, String body) throws IOException, InterruptedException {
        HttpRequest req = HttpRequest.newBuilder()
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .uri(URI.create(URL))
                .build();
        return client.send(req, HttpResponse.BodyHandlers.ofString());
    }
}
