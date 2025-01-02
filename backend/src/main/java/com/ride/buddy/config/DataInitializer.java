// src/main/java/com/ride/buddy/config/DataInitializer.java
package com.ride.buddy.config;

import com.ride.buddy.domain.entities.RideList;
import com.ride.buddy.repositories.RideListRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class DataInitializer implements CommandLineRunner {

    private final RideListRepository rideListRepository;

    public DataInitializer(RideListRepository rideListRepository) {
        this.rideListRepository = rideListRepository;
    }

    @Override
    public void run(String... args) {
        List<RideList> predefinedRideLists = List.of(
                new RideList(UUID.fromString("1e3b8f3e-5c4d-4c3f-8b7a-1234567890ab"), "Evergreen", List.of()),
                new RideList(UUID.fromString("2f4c9e4f-6d5e-5d4f-9c8b-0987654321ba"), "Seton", List.of()),
                new RideList(UUID.fromString("3a5d0f5a-7e6f-6e5a-0d9c-9876543210cb"), "Tuscany", List.of()),
                new RideList(UUID.fromString("4b6e1f6b-8f7f-7f6b-1e0d-8765432109dc"), "Cranston", List.of())
        );

        rideListRepository.saveAll(predefinedRideLists);
        System.out.println("Predefined Ride Lists have been initialized.");
    }
}
