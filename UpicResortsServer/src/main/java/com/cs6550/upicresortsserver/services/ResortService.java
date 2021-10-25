package com.cs6550.upicresortsserver.services;

import com.cs6550.upicresortsserver.models.Resort;
import com.cs6550.upicresortsserver.repositories.ResortRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResortService {

    @Autowired
    ResortRepository repository;

    public List<Resort> getResorts() {
        return (List<Resort>) repository.findAll();
    }
}
