package com.example.rewards.dto;

public class MonthlyReward {
    private long totalPoints;
    private double totalAmount;

    public MonthlyReward() {}

    public MonthlyReward(long totalPoints, double totalAmount) {
        this.totalPoints = totalPoints;
        this.totalAmount = totalAmount;
    }

    public long getTotalPoints() { return totalPoints; }
    public void setTotalPoints(long totalPoints) { this.totalPoints = totalPoints; }
    public double getTotalAmount() { return totalAmount; }
    public void setTotalAmount(double totalAmount) { this.totalAmount = totalAmount; }
}
