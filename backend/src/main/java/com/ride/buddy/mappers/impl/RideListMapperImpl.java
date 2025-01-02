package com.ride.buddy.mappers.impl;

import com.ride.buddy.domain.dto.RideListDto;
import com.ride.buddy.domain.entities.RideList;
import com.ride.buddy.mappers.RideListMapper;
import com.ride.buddy.mappers.RideMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class RideListMapperImpl implements RideListMapper {

    private final RideMapper rideMapper;

    public RideListMapperImpl(RideMapper rideMapper) {
        this.rideMapper = rideMapper;
    }

    @Override
    public RideList fromDto(RideListDto rideListDto) {
        return new RideList(
                rideListDto.id(),
                rideListDto.community(),
                Optional.ofNullable(rideListDto.rides())
                        .map(rides -> rides.stream()
                                .map(rideMapper::fromDto)
                                .toList()
                        ).orElse(null)
        );
    }

    @Override
    public RideListDto toDto(RideList rideList) {
        return new RideListDto(
                rideList.getId(),
                rideList.getCommunity(),
                Optional.ofNullable(rideList.getRides())
                        .map(List::size)
                        .orElse(0),
                Optional.ofNullable(rideList.getRides())
                        .map(rides ->
                                rides.stream().map(rideMapper::toDto).toList()
                        ).orElse(null)
        );
    }
}
