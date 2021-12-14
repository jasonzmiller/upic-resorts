package com.cs6550.upicresortsserver.services;

import com.cs6550.upicresortsserver.exceptions.BadRequestException;
import com.cs6550.upicresortsserver.exceptions.EntityNotFoundException;
import com.cs6550.upicresortsserver.models.LiftRide;
import com.cs6550.upicresortsserver.models.LiftRideRequest;
import com.cs6550.upicresortsserver.models.LiftRidesList;
import com.cs6550.upicresortsserver.repositories.LiftRideRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LiftRideService {

    @Autowired
    private LiftRideRepository liftRideRepository;

    @Autowired
    private SkierService skierService;

    public ResponseEntity<LiftRidesList> getLiftRides() {
        List<LiftRide> rides = (List<LiftRide>) liftRideRepository.findAll();
        return new ResponseEntity<>(new LiftRidesList(rides), HttpStatus.OK);
    }

    public ResponseEntity<LiftRidesList> getLiftRides(String skierId) {
        int skier;
        try {
            skier = Integer.parseInt(skierId);
        } catch (IllegalArgumentException ex) {
            throw new BadRequestException("IDs must be numeric.");
        }
        List<LiftRide> rides = liftRideRepository.findLiftRidesForSkier(skier);
        if (rides.size() == 0) throw new EntityNotFoundException("Data not found.");
        return new ResponseEntity<>(new LiftRidesList(rides), HttpStatus.OK);
    }

    public void deleteAll() {
        liftRideRepository.deleteAll();
    }

    public ResponseEntity<LiftRide> writeNewLiftRide(int resortId, int skierId, int liftId, int time) {
        LiftRide newLiftRide = validateInput(resortId, skierId, liftId, time);
        skierService.createSkierIfAbsent(skierId);
        liftRideRepository.save(newLiftRide);
        newLiftRide = liftRideRepository.findLiftRideBy(resortId, skierId, liftId, time);
        newLiftRide.setUrl("/liftrides/" + newLiftRide.getLiftRideId());
        return new ResponseEntity<>(newLiftRide, HttpStatus.CREATED);
    }

    private LiftRide validateInput(int resortId, int skierId, int liftId, int time) {
        // check if resort IDs are between 1-8
        if (resortId < 1 || resortId > 5) {
            throw new BadRequestException("Resort ID must be between 1 and 5.");
        }

        // check if skier ID is positive
        if (skierId < 1) {
            throw new BadRequestException("Skier ID must be positive.");
        }

        if (liftRideRepository.findLiftRideBy(resortId, skierId, liftId, time) == null)
            throw new BadRequestException("This lift ride already exists.");

        return new LiftRide(resortId, skierId, time, liftId);
    }

    private LiftRide validateInput(String resortId, String skierId, String liftId, String time) {
        int resortIdInt, skierIdInt, liftIdInt, timeInt;
        try {
            resortIdInt = Integer.parseInt(resortId);
            skierIdInt = Integer.parseInt(skierId);
            liftIdInt = Integer.parseInt(liftId);
            timeInt = Integer.parseInt(time);
        } catch (IllegalArgumentException ex) {
            throw new BadRequestException("IDs must be numeric.");
        }

        return validateInput(resortIdInt, skierIdInt, liftIdInt, timeInt);
    }

}
