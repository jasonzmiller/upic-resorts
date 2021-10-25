package com.cs6550.upicresortsserver.controllers;

import com.cs6550.upicresortsserver.services.LiftRideService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LiftRideController {

    @Autowired
    private LiftRideService service;
}
