package com.example.rewards.dto;

import java.util.List;
import java.util.Map;


public class RewardResponseDto {

    private Long customerId;
    private String customerName;
    private Map<String, MonthlyReward> monthlyRewards;
    private List<TransactionResponse> transactions;

    public RewardResponseDto() {}

    public RewardResponseDto(Long customerId, String customerName, Map<String, MonthlyReward> monthlyRewards, List<TransactionResponse> transactions) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.monthlyRewards = monthlyRewards;
        this.transactions = transactions;
    }

    // getters & setters
    public Long getCustomerId() { return customerId; }
    public void setCustomerId(Long customerId) { this.customerId = customerId; }
    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }
    public Map<String, MonthlyReward> getMonthlyRewards() { return monthlyRewards; }
    public void setMonthlyRewards(Map<String, MonthlyReward> monthlyRewards) { this.monthlyRewards = monthlyRewards; }
    public List<TransactionResponse> getTransactions() { return transactions; }
    public void setTransactions(List<TransactionResponse> transactions) { this.transactions = transactions; }
}
