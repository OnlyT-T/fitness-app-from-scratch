package com.progressfit.backend.service;

import com.progressfit.backend.entity.Goal;
import com.progressfit.backend.entity.RunningActivity;

import com.progressfit.backend.repository.GoalRepository;
import com.progressfit.backend.repository.RunningActivityRepository;

import com.progressfit.backend.dto.RunningProgressResponse;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class RunningProgressService {
    
    private final GoalRepository goalRepository;
    private final RunningActivityRepository activityRepository;

    public RunningProgressService(
        GoalRepository goalRepository,
        RunningActivityRepository activityRepository
    ) {
        this.goalRepository = goalRepository;
        this.activityRepository = activityRepository;
    }

    @Transactional(readOnly = true)
    public RunningProgressResponse calculateProgress(
        Long userId,
        Long goalId
    ) {

        // 1. Find Goal
        Goal goal = goalRepository.findById(goalId)
            .orElseThrow(() ->
                new IllegalArgumentException("Goal not found")
            );

        // 2. Verify ownership
        if (!goal.getUser().getId().equals(userId)) {
            throw new IllegalArgumentException(
                "goal does not belong to this user"
            );
        }

        // 3. Find Activities within Goal period
        List<RunningActivity> activities =
            activityRepository.findByUserIdAndPerformedAtBetween(
                userId,
                goal.getStartDate(),
                goal.getEndDate()
            );
    
        // 4. Calculate current progress
        BigDecimal current = BigDecimal.ZERO;
        String unit;

        switch (goal.getMetric()) {

            case DISTANCE -> {
                // .map() lấy từng giá trị Distance của từng Activity
                // sau đó .reduce() sẽ cộng tất cả lại
                // trong đó, tham số đầu là giá trị khởi tạo và tham số còn lại là hàm kết hợp các phần từ
                current = activities.stream()
                    .map(RunningActivity::getDistanceKm)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

                unit = "km";
            }

            case DURATION -> {
                // ở đây ta chỉ cần dùng hàm sum() để tính tổng tất cả dữ liệu trong IntStream
                int totalSeconds = activities.stream()
                    .mapToInt(RunningActivity::getDurationSec)
                    .sum();
                
                current = BigDecimal.valueOf(totalSeconds)
                    .divide(
                        BigDecimal.valueOf(60),
                        2,
                        RoundingMode.HALF_UP
                    );
                
                unit = "minute";
            }

            case PACE -> {

                throw new UnsupportedOperationException(
                    "PACE progress will be implemented separately"
                );
            }

            default -> throw new IllegalArgumentException(
                "Unsupported Goal Metric"
            );
        }

        // 5. Calculate percentage
        BigDecimal percentage = current
            .divide(
                goal.getTarget(),
                4,
                RoundingMode.HALF_UP // Làm tròn như bình thường, nếu rơi vào số 5 thì làm tròn lên
            )
            .multiply(BigDecimal.valueOf(100))
            .setScale(2, RoundingMode.HALF_UP);

        // 6. Check completion
        boolean completed = current.compareTo(goal.getTarget()) >= 0;

        // 7. Retrun Response
        return new RunningProgressResponse(
            goal.getId(),
            goal.getMetric(),
            goal.getTarget(),
            current,
            percentage,
            unit,
            completed
        );
    } 
}
