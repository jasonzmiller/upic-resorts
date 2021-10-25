package com.cs6550.upicresortsserver.models;

import javax.persistence.*;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Resort {
    private @Id @GeneratedValue(strategy = GenerationType.AUTO) Long resortId;
    private String resortName;
    private @ManyToMany Set<Season> seasons;

    public Resort(Long resortId, String resortName) {
        this.resortId = resortId;
        this.resortName = resortName;
        this.seasons = new HashSet<>();
    }

    public Resort() {}

    public Long getResortId() {
        return resortId;
    }

    public String getResortName() {
        return resortName;
    }

    public Set<Season> getSeasons() {
        return Collections.unmodifiableSet(seasons);
    }

    /*TODO - this should be moved to services*/
    public void addSeason(Season season) {
        assert seasons != null : "Internal error. Season list for " + resortId + " has not been initialized.";
        seasons.add(season);
    }
}
