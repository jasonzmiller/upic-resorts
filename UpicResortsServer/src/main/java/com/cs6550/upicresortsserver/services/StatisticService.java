package com.cs6550.upicresortsserver.services;

import com.cs6550.upicresortsserver.models.EndpointRequest;
import com.cs6550.upicresortsserver.models.EndpointStatistic;
import com.cs6550.upicresortsserver.models.EndpointStatisticList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class StatisticService {

    @Autowired
    private EndpointRequestService endpointRequestService;

    public ResponseEntity<EndpointStatisticList> getStatistics() {
        List<EndpointStatistic> stats = new LinkedList<>();
        stats.add(new EndpointStatistic(
                "resorts",
                "GET",
                findMaxLatencyTimeGetRequests(),
                findMaxLatencyTimeGetRequests()
        ));
        return new ResponseEntity<>(new EndpointStatisticList(stats), HttpStatus.OK);
    }

    private long findMaxLatencyTimeGetRequests() {
        Iterable<EndpointRequest> requests = endpointRequestService.findAllByRequestType("GET");
        long maxLatency = 0;
        for (EndpointRequest req : requests) {
            maxLatency += req.getLatencyTime();
        }
        return maxLatency;
    }
}
