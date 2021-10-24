package com.cs6550.upicresortsserver.models;

public class Season {
    private final int seasonId;
    private final String year;

    public Season(int seasonId, String year) {
        this.seasonId = seasonId;
        this.year = year;
    }

    public int getSeasonId() {
        return seasonId;
    }

    public String getYear() {
        return year;
    }
}
