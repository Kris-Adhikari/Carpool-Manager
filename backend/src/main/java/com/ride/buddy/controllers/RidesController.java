// src/main/java/com/ride/buddy/controllers/RidesController.java
package com.ride.buddy.controllers;

import com.ride.buddy.domain.dto.RideDto;
import com.ride.buddy.domain.entities.Ride;
import com.ride.buddy.mappers.RideMapper;
import com.ride.buddy.services.RideService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(path = "/ride-lists/{ride_list_id}/rides")
public class RidesController {

    private final RideService rideService;
    private final RideMapper rideMapper;

    public RidesController(RideService rideService, RideMapper rideMapper) {
        this.rideService = rideService;
        this.rideMapper = rideMapper;
    }

    @GetMapping
    public List<RideDto> listRides(@PathVariable("ride_list_id") UUID rideListId) {
        return rideService.listRides(rideListId)
                .stream()
                .map(rideMapper::toDto)
                .toList();
    }

    @PostMapping
    public RideDto createRide(@PathVariable("ride_list_id") UUID rideListId,
                              @RequestBody RideDto rideDto) {
        Ride createdRide = rideService.createRide(rideListId, rideMapper.fromDto(rideDto));
        return rideMapper.toDto(createdRide);
    }

    @GetMapping(path = "/{ride_id}")
    public Optional<RideDto> getRide(@PathVariable("ride_list_id") UUID rideListId,
                                     @PathVariable("ride_id") UUID rideId) {
        return rideService.getRide(rideListId, rideId)
                .map(rideMapper::toDto);
    }

    @PutMapping(path = "/{ride_id}")
    public RideDto updateRide(@PathVariable("ride_list_id") UUID rideListId,
                              @PathVariable("ride_id") UUID rideId,
                              @RequestBody RideDto rideDto) {
        Ride updatedRide = rideService.updateRide(rideListId, rideId, rideMapper.fromDto(rideDto));
        return rideMapper.toDto(updatedRide);
    }

    @DeleteMapping(path = "/{ride_id}")
    public void deleteRide(@PathVariable("ride_list_id") UUID rideListId,
                           @PathVariable("ride_id") UUID rideId) {
        rideService.deleteRide(rideListId, rideId);
    }
}
