package com.cs6550.upicresortsserver.models;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Season {
    private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long seasonId;
    private String year;
    private @ManyToMany Set<Resort> resorts;

    public Season(Long seasonId, String year) {
        this.seasonId = seasonId;
        this.year = year;
        this.resorts = new HashSet<>();
    }

    public Season() {}

    public Long getSeasonId() {
        return seasonId;
    }

    public String getYear() {
        return year;
    }
}
