// src/main/java/com/ride/buddy/services/impl/RideServiceImpl.java
package com.ride.buddy.services.impl;

import com.ride.buddy.domain.entities.Ride;
import com.ride.buddy.domain.entities.RideList;
import com.ride.buddy.domain.entities.RideStatus;
import com.ride.buddy.domain.entities.RideTime;
import com.ride.buddy.repositories.RideListRepository;
import com.ride.buddy.repositories.RideRepository;
import com.ride.buddy.services.RideService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
public class RideServiceImpl implements RideService {

    private final RideRepository rideRepository;
    private final RideListRepository rideListRepository;

    public RideServiceImpl(RideRepository rideRepository, RideListRepository rideListRepository) {
        this.rideRepository = rideRepository;
        this.rideListRepository = rideListRepository;
    }

    @Override
    public List<Ride> listRides(UUID rideListId) {
        return rideRepository.findByRideListId(rideListId);
    }

    @Transactional
    @Override
    public Ride createRide(UUID rideListId, Ride ride) {
        if (ride.getId() != null) {
            throw new IllegalArgumentException("ride already has an id");
        }
        if (ride.getCar() == null || ride.getCar().isBlank()) {
            throw new IllegalArgumentException("ride must have car");
        }
        if (ride.getContact() == null || ride.getContact().isBlank()) {
            throw new IllegalArgumentException("ride must have contact");
        }

        RideTime rideTime = RideTime.MORNING;
        RideStatus rideStatus = RideStatus.OPEN;

        RideList rideList = rideListRepository.findById(rideListId)
                .orElseThrow(() -> new IllegalArgumentException("invalid ride list id"));

        LocalDateTime now = LocalDateTime.now();

        Ride rideToSave = new Ride(
                ride.getId(),
                ride.getCar(),
                ride.getDeparture(),
                rideStatus,
                rideTime,
                ride.getContact(),
                rideList,
                now,
                now
        );

        return rideRepository.save(rideToSave);
    }

    @Override
    public Optional<Ride> getRide(UUID rideListId, UUID rideId) {
        return rideRepository.findByRideListIdAndId(rideListId, rideId);
    }

    @Transactional
    @Override
    public Ride updateRide(UUID rideListId, UUID rideId, Ride ride) {
        if (ride.getId() == null) {
            throw new IllegalArgumentException("ride must have id");
        }
        if (!Objects.equals(rideId, ride.getId())) {
            throw new IllegalArgumentException("ride ids do not match");
        }
        if (ride.getTime() == null) {
            throw new IllegalArgumentException("ride must have time");
        }
        if (ride.getStatus() == null) {
            throw new IllegalArgumentException("ride must have status");
        }

        Ride existingRide = rideRepository.findByRideListIdAndId(rideListId, rideId)
                .orElseThrow(() -> new IllegalArgumentException("ride not found"));

        existingRide.setCar(ride.getCar());
        existingRide.setContact(ride.getContact());
        existingRide.setDeparture(ride.getDeparture());
        existingRide.setTime(ride.getTime());
        existingRide.setStatus(ride.getStatus());
        existingRide.setUpdated(LocalDateTime.now());

        return rideRepository.save(existingRide);
    }

    @Transactional
    @Override
    public void deleteRide(UUID rideListId, UUID rideId) {
        rideRepository.deleteByRideListIdAndId(rideListId, rideId);
    }
}
