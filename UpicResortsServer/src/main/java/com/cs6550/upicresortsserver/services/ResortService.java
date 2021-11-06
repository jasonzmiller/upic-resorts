package com.cs6550.upicresortsserver.services;

import com.cs6550.upicresortsserver.exceptions.BadRequestException;
import com.cs6550.upicresortsserver.exceptions.EntityNotFoundException;
import com.cs6550.upicresortsserver.models.Resort;
import com.cs6550.upicresortsserver.models.ResortSeasons;
import com.cs6550.upicresortsserver.models.ResortsList;
import com.cs6550.upicresortsserver.repositories.ResortRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class ResortService {

    @Autowired
    private ResortRepository resortRepository;

    public ResponseEntity<ResortsList> getResorts() {
        List<Resort> resorts = (List<Resort>) resortRepository.findAll();
        return new ResponseEntity<>(new ResortsList(resorts), HttpStatus.OK);
    }

    /*
    tests:
    /resorts/notnumeric/seasons - 400
    /resorts/0/seasons          - 400
    /resorts/6/seasons          - 400
    /resorts/1/seasons          - 200
    cannot test EntityNotFoundException (404) because assume all resorts are already stored in DB

     */
    public ResponseEntity<ResortSeasons> getResortSeasons(String resortId) throws BadRequestException, EntityNotFoundException {
        int id;
        try {
            id = Integer.parseInt(resortId);
        } catch (IllegalArgumentException ex) {
            throw new BadRequestException("Invalid Resort ID: ID must be numeric.");
        }
        if (id < 1 || id > 5)
            throw new BadRequestException("Invalid Resort ID: ID must be a numeric value between 1 and 5.");
        if (!resortRepository.existsById(id))
             throw new EntityNotFoundException("Resort with ID: " + resortId + " not found.");
        return new ResponseEntity<>(new ResortSeasons(Arrays.asList("2021")), HttpStatus.OK);
    }
}
