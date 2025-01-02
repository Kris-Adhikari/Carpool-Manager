package com.ride.buddy.repositories;

import com.ride.buddy.domain.entities.RideList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RideListRepository extends JpaRepository<RideList, UUID> {
}
