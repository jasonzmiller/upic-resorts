package com.cs6550.upicresortsserver.repositories;

import com.cs6550.upicresortsserver.models.EndpointRequest;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;


public interface EndpointRequestRepository extends CrudRepository<EndpointRequest, Integer> {
    @Query(value = "SELECT MAX(latency_time) FROM endpoint_requests WHERE request_type=:requestType", nativeQuery = true)
    long findMaxLatencyTime(@Param("requestType") String requestType);

    @Query(value = "SELECT AVG(latency_time) FROM endpoint_requests WHERE request_type=:requestType", nativeQuery = true)
    long findAverageLatencyTime(@Param("requestType") String requestType);

    @Query(value = "SELECT * FROM endpoint_requests WHERE request_type=:requestType", nativeQuery = true)
    Iterable<EndpointRequest> findAllByRequestType(@Param("requestType") String requestType);
}
