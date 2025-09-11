package com.example.rewards.model;

import java.time.Month;
import java.util.Map;

public class RewardResponse {

    private String customerId;
    private Map<Month, Long> monthlyPoints;
    private long totalPoints;

    // Correct constructor
    public RewardResponse(String customerId, Map<Month, Long> monthlyPoints, long totalPoints) {
        this.customerId = customerId;
        this.monthlyPoints = monthlyPoints;
        this.totalPoints = totalPoints;
    }

    // Getters
    public String getCustomerId() {
        return customerId;
    }

    public Map<Month, Long> getMonthlyPoints() {
        return monthlyPoints;
    }

    public long getTotalPoints() {
        return totalPoints;
    }
}
