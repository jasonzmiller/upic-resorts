package com.cs6550.upicresortsserver.controllers;

import com.cs6550.upicresortsserver.exceptions.BadRequestException;
import com.cs6550.upicresortsserver.exceptions.InvalidCredentialsException;
import com.cs6550.upicresortsserver.models.EndpointRequest;
import com.cs6550.upicresortsserver.models.LiftRide;
import com.cs6550.upicresortsserver.models.LiftRideRequest;
import com.cs6550.upicresortsserver.models.LiftRidesList;
import com.cs6550.upicresortsserver.services.EndpointRequestService;
import com.cs6550.upicresortsserver.services.LiftRideService;
import com.cs6550.upicresortsserver.utils.Authentication;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class LiftRideController {

    @Autowired
    private LiftRideService service;

    @Autowired
    private EndpointRequestService endpointRequestService;

    // TODO - "url" : "liftrides/{liftRideId}" - make sure {liftRideID} is added in POST method
    // TODO -
    @GetMapping("/liftrides")
    public ResponseEntity<LiftRidesList> getAllLiftRides(@RequestParam(required = false, name = "skier") String skierId) {
        ResponseEntity<LiftRidesList> response = null;
        if (skierId == null) response = service.getLiftRides();
        else {
            service.getLiftRides(skierId);
        }
        return response;
    }

    @GetMapping("/skiers/{resortId}/seasons/{seasonId}/days/{dayId}/skiers/{skierId}")
    public ResponseEntity<Integer> getSkierDayVertical(@PathVariable("resortId") String resortId,
                                              @PathVariable("dayId") String dayId,
                                              @PathVariable("seasonId") String seasonId,
                                              @PathVariable("skierId") String skierId) {
        long startTime = System.currentTimeMillis();
        ResponseEntity<Integer> response = service.getSkierDayVertical(resortId, dayId, seasonId, skierId);
        endpointRequestService.createNewEndpointRequest(
            new EndpointRequest(
                "GET",
                System.currentTimeMillis() - startTime,
                "/skiers"));
        return response;
    }

    @PostMapping("/skiers/{resortId}/seasons/{seasonId}/days/{dayId}/skiers/{skierId}")
    public ResponseEntity<LiftRide> writeNewLiftRide(@RequestHeader Map<String, String> headers,
                                     @PathVariable("resortId") String resortId,
                                     @PathVariable("seasonId") String seasonId,
                                     @PathVariable("dayId") String dayId,
                                     @PathVariable("skierId") String skierId,
                                     @RequestBody LiftRideRequest liftRideRequest) {
        long startTime = System.currentTimeMillis();
        String authorization = headers.get("authorization");
        ResponseEntity<LiftRide> response = null;
        if (authorization == null)
        {
            throw new BadRequestException("POST request to this URL must have the authorization header.");
        }
        if (Authentication.checkBasicAuth(authorization))
        {
          response = service.writeNewLiftRide(resortId, dayId, seasonId, skierId, liftRideRequest);
        }
        if (response != null)
        {
            endpointRequestService.createNewEndpointRequest(
                new EndpointRequest(
                    "POST",
                    System.currentTimeMillis() - startTime,
                    "/skiers"));
            return response;
        }
        throw new InvalidCredentialsException();
    }
}
