package com.cs6550.upicresortsserver.controllers;

import com.cs6550.upicresortsserver.models.LiftRide;
import com.cs6550.upicresortsserver.models.LiftRideRequest;
import com.cs6550.upicresortsserver.services.LiftRideService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class LiftRideController {

    @Autowired
    private LiftRideService service;

    @GetMapping("/skiers/{resortId}/seasons/{seasonId}/days/{dayId}/skiers/{skierId}")
    public int getSkierDayVertical(@PathVariable("resortId") String resortId,
                                        @PathVariable String dayId,
                                        @PathVariable String seasonId,
                                        @PathVariable String skierId) {
        return service.getSkierDayVertical(resortId, dayId, seasonId, skierId);
    }

    @PostMapping("/skiers/{resortId}/seasons/{seasonId}/days/{dayId}/skiers/{skierId}")
    public LiftRide writeNewLiftRide(@PathVariable("resortId") String resortId,
                                     @PathVariable("seasonId") String seasonId,
                                     @PathVariable("dayId") String dayId,
                                     @PathVariable("skierId") String skierId,
                                     @RequestBody LiftRideRequest liftRideRequest) {
        return service.writeNewLiftRide(resortId, dayId, seasonId, skierId, liftRideRequest);
    }
}
