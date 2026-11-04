package com.progressfit.backend.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.progressfit.backend.dto.RunningActivityRequest;
import com.progressfit.backend.dto.RunningActivityResponse;
import com.progressfit.backend.entity.RunningActivity;
import com.progressfit.backend.entity.User;
import com.progressfit.backend.repository.RunningActivityRepository;
import com.progressfit.backend.repository.UserRepository;

@Service
public class RunningActivityService {
    
    private final RunningActivityRepository activityRepository;
    private final UserRepository userRepository;

    public RunningActivityService(RunningActivityRepository activityRepository, UserRepository userRepository) {
        this.activityRepository = activityRepository;
        this.userRepository = userRepository;                       
    }

    // CREATING (after converting from Entity to DTO)

    @Transactional
    public RunningActivityResponse createActivity(Long userId, RunningActivityRequest request) {

        User user = userRepository.findById(userId).orElseThrow(() ->
            new IllegalArgumentException("User not found")
            );
        
        RunningActivity activity = new RunningActivity();

        activity.setUser(user);
        activity.setPerformedAt(request.performedAt());
        activity.setDistanceKm(request.distanceKm());
        activity.setDurationSec(request.durationSec());
        activity.setCalories(request.calories());
        activity.setNotes(request.notes());

        RunningActivity saved = activityRepository.save(activity);

        return toResponse(saved);
    } 

    // GET RUNNING ACTIVITY HISTORY

    @Transactional(readOnly = true)
    public List<RunningActivityResponse> getAllActivities(Long userId) {

        return activityRepository.findByUserIdOrderByPerformedAtDescIdDesc(userId)
                                 .stream()
                                 .map(this::toResponse)
                                 .toList();
    }

    // GET ACTIVITY BY ID
    
    @Transactional(readOnly = true)
    public RunningActivityResponse getActivityById(Long userId, Long activityId) {

        RunningActivity activity = activityRepository
                                    .findByIdAndUserId(activityId, userId)
                                    .orElseThrow(() ->
                                        new IllegalArgumentException("Running activity not found")
                                    );
        
        return toResponse(activity);
    }

    // DELETE

    @Transactional
    public void deleteActivity(Long userId, Long activityId) {

        RunningActivity activity = activityRepository
                                    .findByIdAndUserId(activityId, userId)
                                    .orElseThrow(() ->
                                        new IllegalArgumentException("Running activity not found")
                                    );
        
        activityRepository.delete(activity);
    }

    // CONVERT FROM ENTITY TO DTO

    private RunningActivityResponse toResponse(RunningActivity activity) {

        BigDecimal distance = activity.getDistanceKm();

        BigDecimal duration = BigDecimal.valueOf(
            activity.getDurationSec()
        );

        // AvgPace = Duration (minutes) / Distance (km)
        BigDecimal avgPace = duration
            .divide(BigDecimal.valueOf(60), 6, RoundingMode.HALF_UP)
            .divide(distance, 2, RoundingMode.HALF_UP);

        // AvgSpeed = Distance (km) / Duration (minutes)
        BigDecimal avgSpeed = distance
            .multiply(BigDecimal.valueOf(3600))
            .divide(duration, 2, RoundingMode.HALF_UP);

        return new RunningActivityResponse(
            activity.getId(),
            activity.getUser().getId(),
            activity.getPerformedAt(),
            distance,
            activity.getDurationSec(),
            activity.getCalories(),
            avgPace,
            avgSpeed,
            activity.getNotes()
        );
    }
}
