package com.ride.buddy.domain.dto;

import java.util.List;
import java.util.UUID;

public record RideListDto(
        UUID id,
        String community,
        Integer count,
        List<RideDto> rides
) {
}
