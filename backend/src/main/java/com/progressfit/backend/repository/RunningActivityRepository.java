package com.progressfit.backend.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.progressfit.backend.entity.RunningActivity;


public interface RunningActivityRepository extends JpaRepository<RunningActivity, Long> {
    List<RunningActivity> findByUserIdOrderByPerformedAtDescIdDesc(Long userId);

    List<RunningActivity> findByUserIdAndPerformedAtBetween(
        Long userId,
        LocalDate endDate,
        LocalDate startDate
    );

    Optional<RunningActivity> findByIdAndUserId(
        Long id,
        Long userId
    );
}
