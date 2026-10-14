package com.progressfit.backend.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.progressfit.backend.dto.GoalRequest;
import com.progressfit.backend.dto.GoalResponse;
import com.progressfit.backend.entity.Goal;
import com.progressfit.backend.entity.User;
import com.progressfit.backend.exception.ResourceNotFoundException;
import com.progressfit.backend.repository.GoalRepository;
import com.progressfit.backend.repository.UserRepository;

@Service
public class GoalService {

    private static final String RUNNING = "RUNNING";
    
    private final GoalRepository goalRepository;
    private final UserRepository userRepository;

    public GoalService(GoalRepository goalRepository, UserRepository userRepository) {
        this.goalRepository = goalRepository;
        this.userRepository = userRepository;
    }

    // CREATE
    @Transactional
    public GoalResponse createGoal(Long userId, GoalRequest request) {

        User user = userRepository.findById(userId).orElseThrow(() ->
            new ResourceNotFoundException("User not found with id: " + userId)
        );

        validateRunningGoal(request);

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

    // READ ALL
    @Transactional(readOnly = true)
    public List<GoalResponse> getGoalsByUser(Long userId) {

        validateUserExists(userId);

        return goalRepository.findByUser_IdAndCategory(userId, RUNNING)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    // READ ONE
    @Transactional(readOnly = true)
    public GoalResponse getGoalById(Long userId, Long goalId) {

        Goal goal = findRunningGoal(userId, goalId);

        return toResponse(goal);
    }

    // UPDATE
    @Transactional
    public GoalResponse updateGoal(
        Long userId,
        Long goalId,
        GoalRequest request
    ) {

        Goal goal = findRunningGoal(userId, goalId);

        validateRunningGoal(request);

        goal.setCategory(RUNNING);
        goal.setExercise(null);
        goal.setMetric(request.metric());
        goal.setTarget(request.target());
        goal.setUnit(request.unit());
        goal.setStartDate(request.start_date());
        goal.setEndDate(request.end_date());

        return toResponse(goalRepository.save(goal));
    }

    // DELETE
    @Transactional
    public void deleteGoal(Long userId, Long goalId) {

        Goal goal = findRunningGoal(userId, goalId);

        goalRepository.delete(goal);
    }

    // VALIDATION
    private void validateRunningGoal(GoalRequest request) {

        if (!RUNNING.equals(request.category())) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "Only RUNNING goals are supported"
            );
        }

        if (request.exercise() != null) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "Running goals must not specify an exercise"
            );
        }

        if (request.metric() == null) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "Goal metric is required"
            );
        }

        // BigDecimal cannot go with operators: >, <, >=, <=
        // Therefore, it needs to have method compareTo(BigDecimal.ZERO) to get -1, 0, or 1
        if (request.target() == null || request.target().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Goal target must be greater than zero");
        }

        if (request.start_date() == null ||
            request.end_date() == null ||
            request.end_date().isBefore(request.start_date())) {
            throw new IllegalArgumentException("Invalid goal date range");
        }

        String expectedUnit = switch (request.metric()) {
            case DISTANCE -> "km";
            case DURATION -> "minutes";
            case PACE -> "min/km";
        };

        if (!expectedUnit.equals(request.unit())) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "Invalid unit for metric " + request.metric()
            );
        }
    }

    // HELPERS
    private void validateUserExists(Long userId) {

        if (!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException(
                "User not found with id: " + userId
            );
        }
    }

    private Goal findRunningGoal(Long userId, Long goalId) {

        return goalRepository.findByIdAndUser_IdAndCategory(
            goalId,
            userId,
            RUNNING
        ).orElseThrow(() ->
            new ResourceNotFoundException("Running goal not found with id: " + goalId)
        );
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
