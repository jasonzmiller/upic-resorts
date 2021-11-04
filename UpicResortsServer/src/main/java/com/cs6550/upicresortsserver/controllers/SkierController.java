package com.cs6550.upicresortsserver.controllers;

import com.cs6550.upicresortsserver.services.SkierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SkierController {

    @Autowired
    private SkierService service;
}
