package com.ride.buddy.mappers;

import com.ride.buddy.domain.dto.RideDto;
import com.ride.buddy.domain.entities.Ride;

public interface RideMapper {

    Ride fromDto(RideDto rideDto);

    RideDto toDto(Ride ride);

}
