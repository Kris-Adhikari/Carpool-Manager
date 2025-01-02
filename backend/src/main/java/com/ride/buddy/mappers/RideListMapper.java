package com.ride.buddy.mappers;

import com.ride.buddy.domain.dto.RideListDto;
import com.ride.buddy.domain.entities.RideList;

public interface RideListMapper {
    RideList fromDto(RideListDto rideListDto);

    RideListDto toDto(RideList rideList);

}
