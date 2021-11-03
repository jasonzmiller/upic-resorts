package com.cs6550.upicresortsserver.models;

public class LiftRideRequest {

    private int time;

    private int liftId;

    public LiftRideRequest() {
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getLiftId() {
        return liftId;
    }

    public void setLiftId(int liftId) {
        this.liftId = liftId;
    }
}
