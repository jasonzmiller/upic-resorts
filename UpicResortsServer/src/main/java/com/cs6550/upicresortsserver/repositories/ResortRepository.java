package com.cs6550.upicresortsserver.repositories;

import com.cs6550.upicresortsserver.models.Resort;
import com.cs6550.upicresortsserver.models.Season;
import org.springframework.data.repository.CrudRepository;

import java.util.Set;

public interface ResortRepository extends CrudRepository<Resort, Integer> {
    // TODO query
    Set<Season> getResortSeasons(String resortId);
}
