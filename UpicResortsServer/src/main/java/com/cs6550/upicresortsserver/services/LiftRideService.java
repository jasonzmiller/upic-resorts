package com.cs6550.upicresortsserver.services;

import com.cs6550.upicresortsserver.models.LiftRide;
import com.cs6550.upicresortsserver.models.LiftRideRequest;
import com.cs6550.upicresortsserver.repositories.LiftRideRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LiftRideService {

    @Autowired
    LiftRideRepository liftRideRepository;

    public int getSkierDayVertical(String resortId, String dayId, String seasonId, String skierId) {
        return liftRideRepository.getSkierDayVertical(resortId, dayId, seasonId, skierId);
    }

    public LiftRide writeNewLiftRide(String resortId, String dayId, String seasonId, String skierId, LiftRideRequest liftRideRequest) {
        LiftRide liftRide = new LiftRide();
        liftRide.setResortId(Integer.parseInt(resortId));
        liftRide.setSeasonId(seasonId);
        liftRide.setDayId(dayId);
        liftRide.setSkierId(Integer.parseInt(skierId));
        liftRide.setTime(liftRideRequest.getTime());
        liftRide.setLiftId(liftRideRequest.getLiftId());
        liftRide.setVertical(10 * liftRideRequest.getLiftId()); // for lift N (N=1...8) at each resort, assume vertical distance is 10N
        return liftRideRepository.save(liftRide);
    }
}
