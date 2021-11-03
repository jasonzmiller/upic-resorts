package com.cs6550.upicresortsserver.models;

import javax.persistence.*;

@Entity
public class Skier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int skierId;

    private String skierName;

    public Skier() {}

    public int getSkierId() {
        return skierId;
    }

    public void setSkierId(int skierId) {
        this.skierId = skierId;
    }

    public String getSkierName() {
        return skierName;
    }

    public void setSkierName(String skierName) {
        this.skierName = skierName;
    }
}
