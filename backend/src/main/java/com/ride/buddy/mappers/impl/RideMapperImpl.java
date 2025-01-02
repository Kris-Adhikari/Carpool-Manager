// src/main/java/com/ride/buddy/mappers/impl/RideMapperImpl.java
package com.ride.buddy.mappers.impl;

import com.ride.buddy.domain.dto.RideDto;
import com.ride.buddy.domain.entities.Ride;
import com.ride.buddy.mappers.RideMapper;
import org.springframework.stereotype.Component;

@Component
public class RideMapperImpl implements RideMapper {

    @Override
    public Ride fromDto(RideDto rideDto) {
        return new Ride(
                rideDto.id(),
                rideDto.car(),
                rideDto.departure(),
                rideDto.status(),
                rideDto.time(),
                rideDto.contact(),
                null,
                null,
                null
        );
    }

    @Override
    public RideDto toDto(Ride ride) {
        return new RideDto(
                ride.getId(),
                ride.getCar(),
                ride.getDeparture(),
                ride.getStatus(),
                ride.getTime(),
                ride.getContact()
        );
    }
}
