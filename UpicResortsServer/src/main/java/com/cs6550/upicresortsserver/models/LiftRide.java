package com.cs6550.upicresortsserver.models;

import javax.persistence.*;

@Entity
@Table(name = "lift_rides")
public class LiftRide {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int liftRideId;

    private int resort;

    private int skier;

    private int time;

    private int liftId;

    private String url;

    public LiftRide() {
        this.url = "/liftrides/";
    }

    public LiftRide(int resort, int skier, int time, int liftId) {
        this.resort = resort;
        this.skier = skier;
        this.time = time;
        this.liftId = liftId;
    }

    public int getLiftRideId() {
        return liftRideId;
    }

    public void setLiftRideId(int liftRideId) {
        this.liftRideId = liftRideId;
    }

    public int getResort() {
        return resort;
    }

    public void setResort(int resort) {
        this.resort = resort;
    }

    public int getSkier() {
        return skier;
    }

    public void setSkier(int skier) {
        this.skier = skier;
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
