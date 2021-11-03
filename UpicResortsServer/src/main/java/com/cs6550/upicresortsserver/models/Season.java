package com.cs6550.upicresortsserver.models;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
public class Season {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seasonId;

    private String year;

    @ManyToMany
    private Set<Resort> resorts;

    public Season(Long seasonId, String year) {
        this.seasonId = seasonId;
        this.year = year;
        this.resorts = new HashSet<>();
    }

    public Season() {}

    public Long getSeasonId() {
        return seasonId;
    }

    public void setSeasonId(Long seasonId) {
        this.seasonId = seasonId;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public Set<Resort> getResorts() {
        return resorts;
    }

    public void setResorts(Set<Resort> resorts) {
        this.resorts = resorts;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Season season = (Season) o;
        return Objects.equals(seasonId, season.seasonId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(seasonId);
    }
}
