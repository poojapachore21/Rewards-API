package com.example.rewards.dto;

import java.time.YearMonth;
import java.util.List;
import java.util.Map;

public class RewardResponseDTO {
    private Long customerId;
    private Map<YearMonth, Integer> monthlyPoints;
    private int totalPoints;
    private List<Transaction> transactions;

    public RewardResponseDTO(Long customerId, Map<YearMonth, Integer> monthlyPoints, int totalPoints,
            List<Transaction> customerTxns) {
        this.customerId = customerId;
        this.monthlyPoints = monthlyPoints;
        this.totalPoints = totalPoints;
        this.transactions = customerTxns;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Map<YearMonth, Integer> getMonthlyPoints() {
        return monthlyPoints;
    }

    public void setMonthlyPoints(Map<YearMonth, Integer> monthlyPoints) {
        this.monthlyPoints = monthlyPoints;
    }

    public int getTotalPoints() {
        return totalPoints;
    }

    public void setTotalPoints(int totalPoints) {
        this.totalPoints = totalPoints;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }
}
