package com.ride.buddy.domain.dto;

import com.ride.buddy.domain.entities.RideStatus;
import com.ride.buddy.domain.entities.RideTime;

import java.time.LocalDateTime;
import java.util.UUID;

public record RideDto(
        UUID id,
        String car,
        LocalDateTime departure,
        RideStatus status,
        RideTime time,
        String contact
) {

}
