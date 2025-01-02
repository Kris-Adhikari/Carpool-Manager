package com.ride.buddy.controllers;

import com.ride.buddy.domain.dto.RideListDto;
import com.ride.buddy.mappers.RideListMapper;
import com.ride.buddy.services.RideListService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(path = "/ride-lists")
public class RideListController {

    private final RideListService rideListService;
    private final RideListMapper rideListMapper;

    public RideListController(RideListService rideListService, RideListMapper rideListMapper) {
        this.rideListService = rideListService;
        this.rideListMapper = rideListMapper;
    }

    @GetMapping
    public List<RideListDto> listRideLists() {
        return rideListService.listRideLists()
                .stream()
                .map(rideListMapper::toDto)
                .toList();
    }

    @GetMapping(path = "/{ride_list_id}")
    public Optional<RideListDto> getRideList(@PathVariable("ride_list_id") UUID rideListId){
        return rideListService.getRideList(rideListId).map(rideListMapper::toDto);
    }

}
