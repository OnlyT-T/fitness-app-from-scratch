package com.progressfit.backend.dto;

import java.math.BigDecimal;

import com.progressfit.backend.enums.GoalMetric;

public record RunningProgressResponse (
    
    Long goalId,

    GoalMetric metric,

    BigDecimal target,

    BigDecimal current,
    
    BigDecimal percentage,

    String unit,

    boolean completed
) {
}
