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
    @GetMapping("/liftrides")
    public ResponseEntity<LiftRidesList> getAllLiftRides(@RequestParam(required = false, name = "skier") String skierId) {
        ResponseEntity<LiftRidesList> response;
        if (skierId == null) response = service.getLiftRides();
        else response = service.getLiftRides(skierId);
        return response;
    }

    @PostMapping("/liftrides")
    public ResponseEntity<LiftRide> writeNewLiftRide(@RequestHeader Map<String, String> headers,
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
            response = service.writeNewLiftRide(
                    liftRideRequest.getResort(),
                    liftRideRequest.getSkier(),
                    liftRideRequest.getLift(),
                    liftRideRequest.getTime());
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
