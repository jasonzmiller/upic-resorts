package com.cs6550.upicresortsserver;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class UpicResortsServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(UpicResortsServerApplication.class, args);
    }

//    @Bean
//    public CommandLineRunner demo() {
//        return args -> {};
//    }

}
