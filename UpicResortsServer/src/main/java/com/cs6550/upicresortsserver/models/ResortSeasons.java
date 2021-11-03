package com.cs6550.upicresortsserver.models;

import java.util.List;

public class ResortSeasons {

    private List<String> seasons;

    public ResortSeasons() {
    }

    public ResortSeasons(List<String> seasons) {
        this.seasons = seasons;
    }

    public List<String> getSeasons() {
        return seasons;
    }

    public void setSeasons(List<String> seasons) {
        this.seasons = seasons;
    }
}
