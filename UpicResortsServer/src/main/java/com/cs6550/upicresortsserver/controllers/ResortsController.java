package com.cs6550.upicresortsserver.controllers;

import com.cs6550.upicresortsserver.models.Resort;
import com.cs6550.upicresortsserver.models.Season;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ResortsController {

    @GetMapping("/resorts")
    public List<Resort> getResorts() {
        return null;
    }

    @GetMapping("/resorts/{resortId}/seasons")
    public List<Season> getResortSeasons(@PathVariable("resortId") Long resortId) {
        return null;
    }


}
