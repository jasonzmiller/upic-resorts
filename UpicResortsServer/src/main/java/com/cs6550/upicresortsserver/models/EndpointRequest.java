package com.cs6550.upicresortsserver.models;

import javax.persistence.*;

@Entity
@Table(name = "endpoint_requests")
public class EndpointRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int requestId;

    private String requestType;

    private long latencyTime;

    private String url;

    public EndpointRequest() {
    }

    public EndpointRequest(String requestType, long latencyTime) {
        this.requestType = requestType;
        this.latencyTime = latencyTime;
    }

    public EndpointRequest(String requestType, long latencyTime, String url) {
        this.requestType = requestType;
        this.latencyTime = latencyTime;
        this.url = url;
    }

    public int getRequestId() {
        return requestId;
    }

    public void setRequestId(int requestId) {
        this.requestId = requestId;
    }

    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    public long getLatencyTime() {
        return latencyTime;
    }

    public void setLatencyTime(long latencyTime) {
        this.latencyTime = latencyTime;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
