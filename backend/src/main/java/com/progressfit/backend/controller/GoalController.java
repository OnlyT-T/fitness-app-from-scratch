package com.progressfit.backend.controller;

import com.progressfit.backend.dto.GoalRequest;
import com.progressfit.backend.dto.GoalResponse;
import com.progressfit.backend.service.GoalService;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/api/users/{userId}/goals")
@CrossOrigin(origins = "http://localhost:3000")
public class GoalController {
    private final GoalService goalService;

    public GoalController(GoalService goalService) {
        this.goalService = goalService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GoalResponse createGoal(
            @PathVariable Long userId,
            @RequestBody GoalRequest request
    ) {
        return goalService.createGoal(userId, request);
    }

    @GetMapping
    public List<GoalResponse> getGoalsByUser(
        @PathVariable Long userId
    ) {
        return goalService.getGoalsByUser(userId);
    }

    @GetMapping("/{goalId}")
    public GoalResponse getGoalById(
        @PathVariable Long userId,
        @PathVariable Long goalId) {
        return goalService.getGoalById(userId, goalId);
    }
    
    @PutMapping("/{goalId}")
    public GoalResponse updateGoal(
        @PathVariable Long userId,
        @PathVariable Long goalId,
        @RequestBody GoalRequest request
    ) {
        return goalService.updateGoal(userId, goalId, request);
    }

    @DeleteMapping("/{goalId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteGoal(
        @PathVariable Long userId,
        @PathVariable Long goalId
    ) {
        goalService.deleteGoal(userId, goalId);
    }
}
