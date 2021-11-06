package com.cs6550.upicresortsserver.services;

import com.cs6550.upicresortsserver.models.EndpointRequest;
import com.cs6550.upicresortsserver.repositories.EndpointRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EndpointRequestService {

    @Autowired
    private EndpointRequestRepository endpointRequestRepository;

    public EndpointRequest createNewEndpointRequest(EndpointRequest endpointRequest) {
        return endpointRequestRepository.save(endpointRequest);
    }

    public Iterable<EndpointRequest> findAllByRequestType(String requestType) {
        return endpointRequestRepository.findAllByRequestType(requestType);
    }

    public void deleteAllStoredRequests() {
        endpointRequestRepository.deleteAll();
    }
}
