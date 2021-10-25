package com.cs6550.upicresortsserver.repositories;

import com.cs6550.upicresortsserver.models.Resort;
import org.springframework.data.repository.CrudRepository;

import java.util.Set;

public interface ResortRepository extends CrudRepository<Resort, Long> {
    Set<Resort> getResorts();
}
