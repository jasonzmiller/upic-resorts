package com.cs6550.upicresortsserver.models;

import java.util.List;

public class LiftRidesList {

    private List<LiftRide> rides;

    public LiftRidesList() {
    }

    public LiftRidesList(List<LiftRide> rides) {
        this.rides = rides;
    }

    public List<LiftRide> getRides() {
        return rides;
    }

    public void setRides(List<LiftRide> rides) {
        this.rides = rides;
    }
}
