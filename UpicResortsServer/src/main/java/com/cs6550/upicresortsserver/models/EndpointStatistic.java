package com.cs6550.upicresortsserver.models;

public class EndpointStatistic {
    private String URL;
    private String operation;
    private Long mean;
    private Long max;

    public EndpointStatistic() {
    }

    public EndpointStatistic(String URL, String operation, long mean, long max) {
        this.URL = URL;
        this.operation = operation;
        this.mean = mean;
        this.max = max;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public Long getMean() {
        return mean;
    }

    public void setMean(Long mean) {
        this.mean = mean;
    }

    public Long getMax() {
        return max;
    }

    public void setMax(Long max) {
        this.max = max;
    }
}
