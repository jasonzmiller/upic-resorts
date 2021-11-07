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
        Iterable<EndpointRequest> getRequests = endpointRequestService.findAllByRequestType("GET");
        if (((List<EndpointRequest>) getRequests).size() > 0) {
            stats.add(new EndpointStatistic(
                    "skiers",
                    "GET",
                    findAverageLatencyTimeRequests(getRequests),
                    findMaxLatencyTimeRequests(getRequests)
            ));
        }
        Iterable<EndpointRequest> postRequests = endpointRequestService.findAllByRequestType("POST");
        if (((List<EndpointRequest>) postRequests).size() > 0) {
            stats.add(new EndpointStatistic(
                "skiers",
                "POST",
                findAverageLatencyTimeRequests(postRequests),
                findMaxLatencyTimeRequests(postRequests)
            ));
        }
        return new ResponseEntity<>(new EndpointStatisticList(stats), HttpStatus.OK);
    }

    public void clearAllStatistics() {
        endpointRequestService.deleteAllStoredRequests();
    }

    private long findMaxLatencyTimeRequests(Iterable<EndpointRequest> requests) {
        long maxLatency = -1L;
        for (EndpointRequest req : requests) {
            if (maxLatency < req.getLatencyTime()) maxLatency = req.getLatencyTime();
        }
        return maxLatency;
    }

    private long findAverageLatencyTimeRequests(Iterable<EndpointRequest> requests) {
        int numRequests = ((List<EndpointRequest>) requests).size();
        numRequests = numRequests == 0 ? 1 : numRequests;
        long totalLatency = 0L;
        for (EndpointRequest req : requests) {
            totalLatency += req.getLatencyTime();
        }
        return totalLatency / ((List<EndpointRequest>) requests).size();
    }
}
