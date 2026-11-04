package com.progressfit.backend.dto;

import java.time.LocalDate;
import java.math.BigDecimal;

public record RunningActivityResponse (

    Long id,
    Long userId,
    LocalDate performedAt,
    BigDecimal distanceKm,
    Integer durationSec,
    Integer calories,
    BigDecimal avgPace,
    BigDecimal avgSpeed,
    String notes
    
) {
}
