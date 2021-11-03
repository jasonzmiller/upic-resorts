package com.cs6550.upicresortsserver.repositories;

import com.cs6550.upicresortsserver.models.LiftRide;
import org.springframework.data.repository.CrudRepository;

public interface LiftRideRepository extends CrudRepository<LiftRide, Integer> {
    // TODO - QUERY
    // TODO - Param("resortId") in parameters below
    int getSkierDayVertical(String resortId, String dayId, String seasonId, String skierId);
}
