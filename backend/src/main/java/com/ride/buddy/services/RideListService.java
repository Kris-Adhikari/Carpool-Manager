package com.ride.buddy.services;

import com.ride.buddy.domain.entities.RideList;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RideListService {
    List<RideList> listRideLists();
    Optional<RideList> getRideList(UUID id);

}
