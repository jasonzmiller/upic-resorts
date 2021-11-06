package com.cs6550.upicresortsserver.services;

import com.cs6550.upicresortsserver.exceptions.BadRequestException;
import com.cs6550.upicresortsserver.exceptions.EntityNotFoundException;
import com.cs6550.upicresortsserver.models.LiftRide;
import com.cs6550.upicresortsserver.models.LiftRideRequest;
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

    /*
    TODO - 200: successful operation
           400: invalid inputs supplied - BadRequestException
           404: data not found - EntityNotFoundException

           resortId: int between 1-8
           dayId: string representation of int 1-366
           seasonId: string representation of "2021"
           skierId: int
     */
    public ResponseEntity<Integer> getSkierDayVertical(String resortId, String dayId, String seasonId, String skierId) throws EntityNotFoundException {
        validateInput(resortId, dayId, seasonId, skierId);
//        List<LiftRide> liftRides = (List<LiftRide>) liftRideRepository.findAll();
//        List<LiftRide> liftRidesForSkier = liftRides
//                .stream()
//                .filter(liftRide -> liftRide.getResortId() == Integer.parseInt(resortId) && liftRide.getDayId().equals(dayId) && liftRide.getSkierId() == Integer.parseInt(skierId))
//                .collect(Collectors.toList());
        List<LiftRide> liftRidesForSkier = liftRideRepository.findLiftRidesByIds(Integer.parseInt(resortId), dayId, Integer.parseInt(skierId));
        int totalVertical = 0;
        for (LiftRide ride : liftRidesForSkier) {
            totalVertical += ride.getVertical();
        }
        if (totalVertical == 0) throw new EntityNotFoundException("data not found");
        ResponseEntity<Integer> res = new ResponseEntity<>(
                totalVertical,
                HttpStatus.OK);
//        ResponseEntity<Integer> res = new ResponseEntity<>(
//                liftRideRepository.getSkierDayVertical(
//                        resortId,
//                        dayId,
//                        seasonId,
//                        skierId),
//                HttpStatus.OK);
        return res;
    }

    /*
    201: write successful - CREATED
    400: invalid inputs supplied - BadRequestException
    404: data not found - EntityNotFoundException

   TODO - CHECK IF THIS LIFT RIDE ALREADY EXISTS
   TODO - 404: data not found - need to check if there

           resortId: int between 1-8
           dayId: string representation of int 1-366
           seasonId: string representation of "2021"
           skierId: int
           liftRideRequest.time: int
           liftRideRequest.liftId: int
     */
    public ResponseEntity<LiftRide> writeNewLiftRide(String resortId, String dayId, String seasonId, String skierId, LiftRideRequest liftRideRequest) {
        validateInput(resortId, dayId, seasonId, skierId, liftRideRequest);
        skierService.createSkierIfAbsent(Integer.parseInt(skierId));
        LiftRide liftRide = buildNewLiftRide(resortId, dayId, seasonId, skierId, liftRideRequest);
        liftRideRepository.save(liftRide);
        return new ResponseEntity<>(liftRide, HttpStatus.CREATED);
    }

    private LiftRide buildNewLiftRide(String resortId, String dayId, String seasonId, String skierId, LiftRideRequest liftRideRequest) {
        LiftRide liftRide = new LiftRide();
        liftRide.setResortId(Integer.parseInt(resortId));
        liftRide.setSeasonId(seasonId);
        liftRide.setDayId(dayId);
        liftRide.setSkierId(Integer.parseInt(skierId));
        liftRide.setTime(liftRideRequest.getTime());
        liftRide.setLiftId(liftRideRequest.getLiftId());
        liftRide.setVertical(10 * liftRideRequest.getLiftId()); // for lift N (N=1...8) at each resort, assume vertical distance is 10N
        return liftRide;
    }

    /*
    TODO - this could be refactored to return an object with the actual parsed values so that we don't need to parse them twice
     */
    private void validateInput(String resortId, String dayId, String seasonId, String skierId) {
        if (!seasonId.equalsIgnoreCase("2021")) {
            throw new BadRequestException("The Season ID must be 2021.");
        }
        int resortIdInt, dayIdInt, skierIdInt;
        try {
            resortIdInt = Integer.parseInt(resortId);
            dayIdInt = Integer.parseInt(dayId);
            skierIdInt = Integer.parseInt(skierId);
        } catch (IllegalArgumentException ex) {
            throw new BadRequestException("IDs must be numeric.");
        }

        // check if resort IDs are between 1-8
        if (resortIdInt < 1 || resortIdInt > 5) {
            throw new BadRequestException("Resort ID must be between 1 and 5.");
        }

        // check if day ID is between 1-366
        if (dayIdInt < 1 || dayIdInt > 366) {
            throw new BadRequestException("Day ID must be between 1 and 366.");
        }

        // check if skier ID is positive
        if (skierIdInt < 1) {
            throw new BadRequestException("Skier ID must be positive.");
        }
    }

    private void validateInput(String resortId, String dayId, String seasonId, String skierId, LiftRideRequest liftRideRequest) {
        validateInput(resortId, dayId, seasonId, skierId);

        // check that lift ID is between 1 and 8
        if (liftRideRequest.getLiftId() < 1 || liftRideRequest.getLiftId() > 8) {
            throw new BadRequestException("Lift ID must be between 1 and 8.");
        }

        // check that time of lift ride is positive
        if (liftRideRequest.getTime() < 0) {
            throw new BadRequestException("Time must be positive.");
        }
    }

}
