package com.progressfit.backend.dto;

import jakarta.validation.constraints.*;

import java.time.LocalDate;
import java.math.BigDecimal;

public record RunningActivityRequest (

    @NotNull
    @PastOrPresent
    LocalDate performedAt,

    @NotNull
    @DecimalMin("0.01")
    BigDecimal distanceKm,

    @NotNull
    @Positive
    Integer durationSec,
    
    @PositiveOrZero
    Integer calories,

    @Size(max=1000)
    String notes
) {}
