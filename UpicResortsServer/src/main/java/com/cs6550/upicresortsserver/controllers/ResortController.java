package com.cs6550.upicresortsserver.controllers;

import com.cs6550.upicresortsserver.models.EndpointRequest;
import com.cs6550.upicresortsserver.models.ResortSeasons;
import com.cs6550.upicresortsserver.models.ResortsList;
import com.cs6550.upicresortsserver.services.EndpointRequestService;
import com.cs6550.upicresortsserver.services.ResortService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ResortController {

    @Autowired
    private ResortService resortService;


    @GetMapping("/resorts")
    public ResponseEntity<ResortsList> getResorts() {
        ResponseEntity<ResortsList> response = resortService.getResorts();
        return response;
    }

    @GetMapping("/resorts/{resortId}/seasons")
    public ResponseEntity<ResortSeasons> getResortSeasons(@PathVariable("resortId") String resortId) {
        return resortService.getResortSeasons(resortId);
    }
}
