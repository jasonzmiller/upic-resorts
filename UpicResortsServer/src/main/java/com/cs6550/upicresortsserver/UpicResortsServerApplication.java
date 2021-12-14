package com.cs6550.upicresortsserver;

import com.cs6550.upicresortsserver.services.LiftRideService;
import com.cs6550.upicresortsserver.services.SkierService;
import com.cs6550.upicresortsserver.services.StatisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class UpicResortsServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(UpicResortsServerApplication.class, args);
    }

    @Autowired
    private StatisticService statisticService;

    @Autowired
    private LiftRideService liftRideService;

    @Autowired
    private SkierService skierService;

    @Bean
    public CommandLineRunner initDatabase() {
        return args -> {
            statisticService.clearAllStatistics();
            liftRideService.deleteAll();
            skierService.deleteAll();
        };
    }

}
