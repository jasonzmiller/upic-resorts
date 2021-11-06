package com.cs6550.upicresortsserver.models;

import javax.persistence.*;

@Entity
@Table(name = "skiers")
public class Skier {
    @Id
    private int skierId;

    private String skierName;

    public Skier() {}

    public Skier(int skierId, String skierName) {
        this.skierId = skierId;
        this.skierName = skierName;
    }

    public Skier(int skierId) {
        this.skierId = skierId;
        this.skierName = "n/a";
    }

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
