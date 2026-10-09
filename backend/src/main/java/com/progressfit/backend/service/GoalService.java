package com.progressfit.backend.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.progressfit.backend.dto.GoalRequest;
import com.progressfit.backend.dto.GoalResponse;
import com.progressfit.backend.entity.Goal;
import com.progressfit.backend.entity.User;
import com.progressfit.backend.exception.ResourceNotFoundException;
import com.progressfit.backend.repository.GoalRepository;
import com.progressfit.backend.repository.UserRepository;

@Service
public class GoalService {
    
    private final GoalRepository goalRepository;
    private final UserRepository userRepository;

    public GoalService(GoalRepository goalRepository, 
                       UserRepository userRepository) {
        this.goalRepository = goalRepository;
        this.userRepository = userRepository;
    }

    @Transactional // 
    public GoalResponse createGoal(Long userId, GoalRequest request) {

        User user = userRepository.findById(userId).orElseThrow(() ->
            new ResourceNotFoundException("User not found with id: " + userId)
        );

        if (request.target() == null || request.target().signum() <= 0) {
            throw new IllegalArgumentException("Goal target must be greater than zero");
        }

        if (request.start_date() == null ||
            request.end_date() == null ||
            request.end_date().isBefore(request.start_date())) {
            throw new IllegalArgumentException("Invalid goal date range");
        }

        Goal goal = new Goal();

        goal.setUser(user);
        goal.setCategory(request.category());
        goal.setExercise(request.exercise());
        goal.setMetric(request.metric());
        goal.setTarget(request.target());
        goal.setUnit(request.unit());
        goal.setStartDate(request.start_date());
        goal.setEndDate(request.end_date());
        goal.setActive(true);

        Goal savedGoal = goalRepository.save(goal);

        return toResponse(savedGoal);
    }

    @Transactional(readOnly = true)
    public List<GoalResponse> getGoalsByUser(Long userId) {

        if(!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException("User not found with id: " + userId);
        }

        return goalRepository.findByUserId(userId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    private GoalResponse toResponse(Goal goal) {
        return new GoalResponse(
            goal.getId(),
            goal.getUser().getId(),
            goal.getCategory(),
            goal.getExercise(),
            goal.getMetric(),
            goal.getTarget(),
            goal.getUnit(),
            goal.getStartDate(),
            goal.getEndDate(),
            goal.isActive()
        );
    }
}
