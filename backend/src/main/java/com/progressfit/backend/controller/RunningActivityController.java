package com.progressfit.backend.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.progressfit.backend.dto.RunningActivityRequest;
import com.progressfit.backend.dto.RunningActivityResponse;
import com.progressfit.backend.service.RunningActivityService;

@RestController
@RequestMapping("api/users/{userId}/running-activities")
@CrossOrigin(origins = "http://localhost:3000")
public class RunningActivityController {
    private final RunningActivityService activityService;

    public RunningActivityController(RunningActivityService activityService) {
        this.activityService = activityService;
    }

    // POST

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RunningActivityResponse createRunningActivity(
            @PathVariable Long userId,
            @RequestBody RunningActivityRequest request
    ) {
        return activityService.createActivity(userId, request);
    }

    // GET ALL

    @GetMapping
    public List<RunningActivityResponse> getAllActivitiesByUser(
        @PathVariable Long userId
    ) {
        return activityService.getAllActivities(userId);
    }

    // GET ACTIVTY BY ID

    @GetMapping("/{activityId}")
    public RunningActivityResponse getActivityById(
        @PathVariable Long userId,
        @PathVariable Long activityId
    ) {
        return activityService.getActivityById(userId, activityId);
    }

    // DELETE

    @DeleteMapping("/{activityId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteActivity(
        @PathVariable Long userId,
        @PathVariable Long activityId
    ) {
        activityService.deleteActivity(userId, activityId);
    }
}
