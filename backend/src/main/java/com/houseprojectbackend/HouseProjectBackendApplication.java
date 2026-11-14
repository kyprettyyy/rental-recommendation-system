package com.houseprojectbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class HouseProjectBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(HouseProjectBackendApplication.class, args);
    }

}
