package com.cs6550.upicresortsserver.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class LiftRide {
    private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) int resortId;
    private int seasonId;
    private int dayId;
    private int skierId;
    private int vertical;
    private int liftId;

    public LiftRide(int resortId, int seasonId, int dayId, int skierId, int liftId, int vertical) {
        this.resortId = resortId;
        this.seasonId = seasonId;
        this.dayId = dayId;
        this.skierId = skierId;
        this.liftId = liftId;
        this.vertical = vertical;
    }

    public LiftRide() {}

    public int getResortId() {
        return resortId;
    }

    public int getSeasonId() {
        return seasonId;
    }

    public int getDayId() {
        return dayId;
    }

    public int getSkierId() {
        return skierId;
    }

    public int getLiftId() {
        return liftId;
    }

    public int getVertical() {
        return vertical;
    }
}
