package com.progressfit.backend.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.progressfit.backend.enums.GoalMetric;

public record GoalRequest (
    String category,
    String exercise,
    GoalMetric metric,
    BigDecimal target,
    String unit,
    LocalDate start_date,
    LocalDate end_date
) {
}