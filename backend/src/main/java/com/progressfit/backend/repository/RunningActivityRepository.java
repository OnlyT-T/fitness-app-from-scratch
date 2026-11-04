package com.progressfit.backend.repository;

import com.progressfit.backend.entity.RunningActivity;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface RunningActivityRepository extends JpaRepository<RunningActivity, Long> {
    List<RunningActivity> findByUserIdOrderByPerformedAtDescIdDesc(Long userId);

    Optional<RunningActivity> findByIdAndUserId(
        Long id,
        Long userId
    );
}
