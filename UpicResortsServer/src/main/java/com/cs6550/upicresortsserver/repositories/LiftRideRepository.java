package com.cs6550.upicresortsserver.repositories;

import com.cs6550.upicresortsserver.models.LiftRide;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LiftRideRepository extends CrudRepository<LiftRide, Integer> {
    @Query(value = "SELECT * " +
                   "FROM lift_rides " +
                   "WHERE skier_id=:skierId", nativeQuery = true)
    List<LiftRide> findLiftRidesForSkier(@Param("skierId") int skierId);

    @Query(value = "SELECT * " +
                   "FROM lift_rides " +
                   "WHERE resort_id=:resortId AND skier_id=:skierId AND lift_id=:liftId AND time=:time", nativeQuery = true)
    LiftRide findLiftRideBy(@Param("resortId") int resortId,
                            @Param("skierId") int skierId,
                            @Param("liftId") int liftId,
                            @Param("time") int time);
}
