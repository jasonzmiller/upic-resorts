package com.cs6550.upicresortsserver.services;

import com.cs6550.upicresortsserver.models.Skier;
import com.cs6550.upicresortsserver.repositories.SkierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SkierService {

    @Autowired
    SkierRepository repository;

    public Skier createSkier(Skier skier) {
        return repository.save(skier);
    }

    public Skier getSkierById(String skierId) {
        return repository.findById(Integer.parseInt(skierId)).get();
    }

    public Optional<Skier> getSkierById(int skierId) {
        return repository.findById(skierId);
    }

    public void createSkierIfAbsent(int skierId) {
        Optional<Skier> optSkier = repository.findById(skierId);
        if (optSkier.isEmpty()) {
            Skier skier = new Skier(skierId);
            repository.save(skier);
        }
    }
}
