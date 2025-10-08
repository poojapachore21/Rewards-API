package com.example.rewards.dto;

import java.util.List;
import java.util.Map;

public class RewardResponseDto {

    private CustomerDto customer;
    private Map<String, Long> monthlyPoints;
    private Long totalPoints;
    private List<PurchaseDto> transactions;

    public RewardResponseDto() {}

    public RewardResponseDto(CustomerDto customer, Map<String, Long> monthlyPoints, Long totalPoints, List<PurchaseDto> transactions) {
        this.customer = customer;
        this.monthlyPoints = monthlyPoints;
        this.totalPoints = totalPoints;
        this.transactions = transactions;
    }

    // getters & setters
    public CustomerDto getCustomer() { return customer; }
    public Map<String, Long> getMonthlyPoints() { return monthlyPoints; }
    public Long getTotalPoints() { return totalPoints; }
    public List<PurchaseDto> getTransactions() { return transactions; }
    public void setCustomer(CustomerDto customer) { this.customer = customer; }
    public void setMonthlyPoints(Map<String, Long> monthlyPoints) { this.monthlyPoints = monthlyPoints; }
    public void setTotalPoints(Long totalPoints) { this.totalPoints = totalPoints; }
    public void setTransactions(List<PurchaseDto> transactions) { this.transactions = transactions; }
}
