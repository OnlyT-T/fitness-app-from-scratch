package com.progressfit.backend.controller;

import com.progressfit.backend.dto.GoalRequest;
import com.progressfit.backend.dto.GoalResponse;
import com.progressfit.backend.service.GoalService;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
}
