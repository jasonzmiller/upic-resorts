package com.cs6550.upicresortsserver.services;

import com.cs6550.upicresortsserver.repositories.LiftRideRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LiftRideService {

    @Autowired
    LiftRideRepository repository;
}
