package com.cs6550.upicresortsserver.controllers;

import com.cs6550.upicresortsserver.services.SkierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SkierController {

    @Autowired
    private SkierService service;

    @GetMapping("/skiers/{resortId}/seasons/{seasonId}/days/{dayId}/skiers/{skierId}")
    public int getSkierDayVertical(@PathVariable("resortId") String resortId,
                                   @PathVariable("seasonId") String seasonId,
                                   @PathVariable("dayId") String dayId,
                                   @PathVariable("skierId") String skierId) {
        return 0;
    }

    @GetMapping("/skiers/{skierId}/vertical")
    public int getSkierResortTotals(@PathVariable("skierId") String skierId) {
        return 0;
    }
}
