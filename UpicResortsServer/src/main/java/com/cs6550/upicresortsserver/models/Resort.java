package com.cs6550.upicresortsserver.models;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Resort {
    private final int resortId;
    private final String resortName;
    private final Set<Season> seasons = new HashSet<>();

    public Resort(int resortId, String resortName) {
        this.resortId = resortId;
        this.resortName = resortName;
    }

    public int getResortId() {
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
