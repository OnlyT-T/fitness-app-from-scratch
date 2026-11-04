package com.progressfit.backend.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity 
@Table(name = "running_activities")
public class RunningActivity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "performed_at", nullable = false)
    private LocalDate performedAt;

    @Column(
        name = "distance_km",
        nullable = false,
        precision = 10,
        scale = 2
    )
    private BigDecimal distanceKm;

    @Column(name = "duration_sec", nullable = false)
    private Integer durationSec;

    private Integer calories;

    @Column(length = 1000)
    private String notes;
    
    public RunningActivity() {
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDate getPerformedAt() {
        return performedAt;
    }

    public void setPerformedAt(LocalDate performedAt) {
        this.performedAt = performedAt;
    }

    public BigDecimal getDistanceKm() {
        return distanceKm;
    }

    public void setDistanceKm(BigDecimal distanceKm) {
        this.distanceKm = distanceKm;
    }

    public Integer getDurationSec() {
        return durationSec;
    }
    
    public void setDurationSec(Integer duration_sec) {
        this.durationSec = duration_sec;
    }

    public Integer getCalories() {
        return calories;
    }

    public void setCalories(Integer calories) {
        this.calories = calories;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
