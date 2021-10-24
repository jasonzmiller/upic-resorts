package com.cs6550.upicresortsserver.models;

public class LiftRide {
    private final int resortId;
    private final int seasonId;
    private final int dayId;
    private final int skierId;
    private final int liftId;
    private final int vertical;

    public LiftRide(int resortId, int seasonId, int dayId, int skierId, int liftId, int vertical) {
        this.resortId = resortId;
        this.seasonId = seasonId;
        this.dayId = dayId;
        this.skierId = skierId;
        this.liftId = liftId;
        this.vertical = vertical;
    }

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
