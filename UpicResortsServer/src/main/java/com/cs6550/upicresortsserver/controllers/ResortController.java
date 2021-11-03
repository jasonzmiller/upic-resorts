package com.cs6550.upicresortsserver.controllers;

import com.cs6550.upicresortsserver.models.Resort;
import com.cs6550.upicresortsserver.models.ResortSeasons;
import com.cs6550.upicresortsserver.services.ResortService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
public class ResortController {

    @Autowired
    private ResortService service;

    @GetMapping("/resorts")
    @ResponseStatus(code = HttpStatus.OK, reason = "Successful operation, empty list if no data")
    public List<Resort> getResorts() {
        return service.getResorts();
    }

    @GetMapping("/resorts/{resortId}/seasons")
    public ResortSeasons getResortSeasons() {
        return new ResortSeasons(Arrays.asList("2021"));
    }
}
