package com.cs6550.upicresortsserver.models;

public class Skier {
    private final int skierId;
    private final String skierName;

    public Skier(int skierId, String skierName) {
        this.skierId = skierId;
        this.skierName = skierName;
    }

    public int getSkierId() {
        return skierId;
    }

    public String getSkierName() {
        return skierName;
    }
}
