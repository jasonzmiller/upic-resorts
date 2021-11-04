package com.cs6550.upicresortsserver.services;

import com.cs6550.upicresortsserver.exceptions.EntityNotFoundException;
import com.cs6550.upicresortsserver.models.LiftRide;
import com.cs6550.upicresortsserver.models.LiftRideRequest;
import com.cs6550.upicresortsserver.repositories.LiftRideRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class LiftRideService {

    @Autowired
    LiftRideRepository liftRideRepository;

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

        if (!resortId.equalsIgnoreCase("2021")) throw new EntityNotFoundException("Data not found");
        ResponseEntity<Integer> res = new ResponseEntity<>(
                liftRideRepository.getSkierDayVertical(
                        resortId,
                        dayId,
                        seasonId,
                        skierId),
                HttpStatus.OK);
        return res;
    }

    /*
    TODO - 201: write successful - CREATED
           400: invalid inputs supplied - BadRequestException
           404: data not found - EntityNotFoundException

           resortId: int between 1-8
           dayId: string representation of int 1-366
           seasonId: string representation of "2021"
           skierId: int
           liftRideRequest.time: int
           liftRideRequest.liftId: int
     */
    public ResponseEntity<LiftRide> writeNewLiftRide(String resortId, String dayId, String seasonId, String skierId, LiftRideRequest liftRideRequest) {
        LiftRide liftRide = new LiftRide();
        liftRide.setResortId(Integer.parseInt(resortId));
        liftRide.setSeasonId(seasonId);
        liftRide.setDayId(dayId);
        liftRide.setSkierId(Integer.parseInt(skierId));
        liftRide.setTime(liftRideRequest.getTime());
        liftRide.setLiftId(liftRideRequest.getLiftId());
        liftRide.setVertical(10 * liftRideRequest.getLiftId()); // for lift N (N=1...8) at each resort, assume vertical distance is 10N
        liftRideRepository.save(liftRide);
        return new ResponseEntity<>(liftRide, HttpStatus.CREATED);
    }


}
