package com.cs6550.upicresortsserver.services;

import com.cs6550.upicresortsserver.models.Resort;
import com.cs6550.upicresortsserver.models.ResortSeasons;
import com.cs6550.upicresortsserver.models.ResortsList;
import com.cs6550.upicresortsserver.repositories.ResortRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Arrays;
import java.util.List;

@Service
public class ResortService {

    @Autowired
    ResortRepository resortRepository;

    public ResponseEntity<ResortsList> getResorts() {
        List<Resort> resorts = (List<Resort>) resortRepository.findAll();
        return new ResponseEntity<>(new ResortsList(resorts), HttpStatus.OK);
    }

    /*
    TODO - 400: Invalid Resort ID supplied
     */
    public ResponseEntity<ResortSeasons> getResortSeasons(String resortId) {
        if (!resortRepository.existsById(Integer.parseInt(resortId))) {
            throw new EntityNotFoundException("Resort with ID:" + resortId + " not found.");
        }
        return new ResponseEntity<>(new ResortSeasons(Arrays.asList("2021")), HttpStatus.OK);
    }
}
