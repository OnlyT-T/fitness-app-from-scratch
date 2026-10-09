package com.progressfit.backend.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record GoalRequest (
    String category,
    String exercise,
    String metric,
    BigDecimal target,
    String unit,
    LocalDate start_date,
    LocalDate end_date
) {
}