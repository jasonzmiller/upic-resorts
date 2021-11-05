package com.cs6550.upicresortsserver.models;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name ="resorts")
public class Resort {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int resortId;

    private String resortName;

    public Resort() {}

    public int getResortId() {
        return resortId;
    }

    public void setResortId(int resortId) {
        this.resortId = resortId;
    }

    public String getResortName() {
        return resortName;
    }

    public void setResortName(String resortName) {
        this.resortName = resortName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Resort resort = (Resort) o;
        return Objects.equals(resortId, resort.resortId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(resortId);
    }
}
