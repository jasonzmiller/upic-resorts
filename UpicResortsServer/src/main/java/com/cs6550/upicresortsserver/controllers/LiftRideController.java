package com.cs6550.upicresortsserver.controllers;

import com.cs6550.upicresortsserver.exceptions.BadRequestException;
import com.cs6550.upicresortsserver.exceptions.InvalidCredentialsException;
import com.cs6550.upicresortsserver.models.LiftRide;
import com.cs6550.upicresortsserver.models.LiftRideRequest;
import com.cs6550.upicresortsserver.services.LiftRideService;
import com.cs6550.upicresortsserver.utils.Authentication;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class LiftRideController {

    @Autowired
    private LiftRideService service;

    @GetMapping("/skiers/{resortId}/seasons/{seasonId}/days/{dayId}/skiers/{skierId}")
    public ResponseEntity<Integer> getSkierDayVertical(@PathVariable("resortId") String resortId,
                                              @PathVariable("dayId") String dayId,
                                              @PathVariable("seasonId") String seasonId,
                                              @PathVariable("skierId") String skierId) {
        return service.getSkierDayVertical(resortId, dayId, seasonId, skierId);
    }

    @PostMapping("/skiers/{resortId}/seasons/{seasonId}/days/{dayId}/skiers/{skierId}")
    public ResponseEntity<LiftRide> writeNewLiftRide(@RequestHeader Map<String, String> headers,
                                     @PathVariable("resortId") String resortId,
                                     @PathVariable("seasonId") String seasonId,
                                     @PathVariable("dayId") String dayId,
                                     @PathVariable("skierId") String skierId,
                                     @RequestBody LiftRideRequest liftRideRequest) {
        String authorization = headers.get("authorization");
        if (authorization == null)
        {
            throw new BadRequestException("POST request to this URL must have the authorization header.");
        }
        if (Authentication.checkBasicAuth(authorization))
        {
          return service.writeNewLiftRide(resortId, dayId, seasonId, skierId, liftRideRequest);
        }
        else throw new InvalidCredentialsException();
    }
}
