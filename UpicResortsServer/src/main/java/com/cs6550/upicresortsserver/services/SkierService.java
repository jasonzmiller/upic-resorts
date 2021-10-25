package com.cs6550.upicresortsserver.services;

import com.cs6550.upicresortsserver.repositories.SkierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SkierService {

    @Autowired
    SkierRepository repository;
}
