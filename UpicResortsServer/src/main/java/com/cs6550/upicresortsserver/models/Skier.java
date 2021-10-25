package com.cs6550.upicresortsserver.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Skier {
    private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) int skierId;
    private String skierName;

    public Skier(int skierId, String skierName) {
        this.skierId = skierId;
        this.skierName = skierName;
    }

    public Skier() {}

    public int getSkierId() {
        return skierId;
    }

    public String getSkierName() {
        return skierName;
    }
}
