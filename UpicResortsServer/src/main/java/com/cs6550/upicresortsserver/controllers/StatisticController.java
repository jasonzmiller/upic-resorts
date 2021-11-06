package com.cs6550.upicresortsserver.controllers;

import com.cs6550.upicresortsserver.models.EndpointStatisticList;
import com.cs6550.upicresortsserver.services.StatisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StatisticController {

    @Autowired
    StatisticService statisticService;

    @GetMapping("/statistics")
    public ResponseEntity<EndpointStatisticList> getStatistics() {
        return statisticService.getStatistics();
    }

}
