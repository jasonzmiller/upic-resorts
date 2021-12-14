package com.cs6550.upicresortsserver.models;

public class LiftRideRequest {

    private int skier;

    private int resort;

    private int time;

    private int lift;

    public LiftRideRequest() {
    }

    public int getSkier() {
        return skier;
    }

    public void setSkier(int skier) {
        this.skier = skier;
    }

    public int getResort() {
        return resort;
    }

    public void setResort(int resort) {
        this.resort = resort;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getLift() {
        return lift;
    }

    public void setLift(int lift) {
        this.lift = lift;
    }
}
