package com.progressfit.backend.controller;

import com.progressfit.backend.service.RunningProgressService;
import com.progressfit.backend.dto.RunningProgressResponse;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users/{userId}/goals")
public class RunningProgressController {
    
    private final RunningProgressService progressService;

    public RunningProgressController(RunningProgressService progressService) {
        this.progressService = progressService;
    }

    @GetMapping("/{goalId}/progress")
    public RunningProgressResponse getProgress(
        @PathVariable Long userId,
        @PathVariable Long goalId
    ) {
        return progressService.calculateProgress(userId, goalId);
    }
}
