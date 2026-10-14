package com.progressfit.backend.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.progressfit.backend.enums.GoalMetric;

public record GoalResponse (
        Long id,
        Long userId, // Response only contains userId, not the whole object User
        String category,
        String exercise,
        GoalMetric metric,
        BigDecimal target,
        String unit,
        LocalDate start_date,
        LocalDate end_date,
        boolean is_active
) {
}
