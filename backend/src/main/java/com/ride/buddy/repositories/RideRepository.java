package com.ride.buddy.repositories;

import com.ride.buddy.domain.entities.Ride;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface RideRepository extends JpaRepository<Ride, UUID> {
    List<Ride> findByRideListId(UUID rideListId);
    Optional<Ride> findByRideListIdAndId(UUID rideListId, UUID id);
    void deleteByRideListIdAndId(UUID rideListId, UUID id);
}
