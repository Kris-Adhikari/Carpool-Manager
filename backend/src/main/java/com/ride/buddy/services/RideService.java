package com.ride.buddy.services;

import com.ride.buddy.domain.entities.Ride;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RideService {
    List<Ride> listRides(UUID rideListId);
    Ride createRide(UUID rideListId, Ride ride);
    Optional<Ride> getRide(UUID rideListId, UUID rideId);
    Ride updateRide(UUID rideListId, UUID rideId, Ride ride);
    void deleteRide(UUID rideListId, UUID rideId);

}
