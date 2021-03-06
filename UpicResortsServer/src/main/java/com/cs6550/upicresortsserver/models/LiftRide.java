package com.cs6550.upicresortsserver.models;

import javax.persistence.*;

@Entity
@Table(name = "lift_rides")
public class LiftRide {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int liftRideId;

    private int resortId;

    private int skierId;

    private int time;

    private int liftId;

    private String url;

    public LiftRide() {
        this.url = "/liftrides/";
    }

    public LiftRide(int resortId, int skierId, int time, int liftId) {
        this.resortId = resortId;
        this.skierId = skierId;
        this.time = time;
        this.liftId = liftId;
    }

    public int getLiftRideId() {
        return liftRideId;
    }

    public void setLiftRideId(int liftRideId) {
        this.liftRideId = liftRideId;
    }

    public int getResortId() {
        return resortId;
    }

    public void setResortId(int resortId) {
        this.resortId = resortId;
    }

    public int getSkierId() {
        return skierId;
    }

    public void setSkierId(int skierId) {
        this.skierId = skierId;
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
