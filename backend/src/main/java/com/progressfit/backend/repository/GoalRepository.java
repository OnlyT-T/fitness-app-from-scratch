package com.progressfit.backend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.progressfit.backend.entity.Goal;

public interface GoalRepository extends JpaRepository<Goal, Long> {
    
    List<Goal> findByUser_IdAndCategory(
        Long userId,
        String category
    );
    
    Optional<Goal> findByIdAndUser_IdAndCategory(
        Long goalId,
        Long userId,
        String category
    );
}
