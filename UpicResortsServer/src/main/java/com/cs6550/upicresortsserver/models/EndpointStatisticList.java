package com.cs6550.upicresortsserver.models;

import java.util.List;

public class EndpointStatisticList {
    private List<EndpointStatistic> endpointStats;

    public EndpointStatisticList() {
    }

    public EndpointStatisticList(List<EndpointStatistic> endpointStats) {
        this.endpointStats = endpointStats;
    }

    public List<EndpointStatistic> getEndpointStats() {
        return endpointStats;
    }

    public void setEndpointStats(List<EndpointStatistic> endpointStats) {
        this.endpointStats = endpointStats;
    }
}
