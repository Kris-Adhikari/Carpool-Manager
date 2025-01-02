package com.ride.buddy.services.impl;

import com.ride.buddy.domain.entities.RideList;
import com.ride.buddy.repositories.RideListRepository;
import com.ride.buddy.services.RideListService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class RideListServiceImpl implements RideListService {

    private final RideListRepository rideListRepository;

    public RideListServiceImpl(RideListRepository rideListRepository) {
        this.rideListRepository = rideListRepository;
    }

    @Override
    public List<RideList> listRideLists() {
        return rideListRepository.findAll();
    }

    @Override
    public Optional<RideList> getRideList(UUID id) {
        return rideListRepository.findById(id);
    }
}
