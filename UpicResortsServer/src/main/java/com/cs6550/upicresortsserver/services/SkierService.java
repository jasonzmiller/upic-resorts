package com.cs6550.upicresortsserver.services;

import com.cs6550.upicresortsserver.models.Skier;
import com.cs6550.upicresortsserver.repositories.SkierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SkierService {

    @Autowired
    private SkierRepository skierRepository;

    public Skier createSkier(Skier skier) {
        return skierRepository.save(skier);
    }

    public Skier getSkierById(String skierId) {
        return skierRepository.findById(Integer.parseInt(skierId)).get();
    }

    public Optional<Skier> getSkierById(int skierId) {
        return skierRepository.findById(skierId);
    }

    public void createSkierIfAbsent(int skierId) {
        Optional<Skier> optSkier = skierRepository.findById(skierId);
        if (optSkier.isEmpty()) {
            Skier skier = new Skier(skierId);
            skierRepository.save(skier);
        }
    }

    public void deleteAll() {
        skierRepository.deleteAll();
    }
}
