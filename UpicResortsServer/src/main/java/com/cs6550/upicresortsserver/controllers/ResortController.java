package com.cs6550.upicresortsserver.controllers;

import com.cs6550.upicresortsserver.models.ResortSeasons;
import com.cs6550.upicresortsserver.models.ResortsList;
import com.cs6550.upicresortsserver.services.ResortService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ResortController {

    @Autowired
    private ResortService service;

    @GetMapping("/resorts")
    public ResponseEntity<ResortsList> getResorts() {
        return service.getResorts();
    }

    @GetMapping("/resorts/{resortId}/seasons")
    public ResponseEntity<ResortSeasons> getResortSeasons(@PathVariable("resortId") String resortId) {
        return service.getResortSeasons(resortId);
    }
}
