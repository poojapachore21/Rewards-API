package com.rewards.reward_api.model;

import java.time.LocalDate;

@Entity
public class Transaction {

    @Id
    private Long id;

    private Long customerId;
    private Double amount;
    private LocalDate transactionDate;

    public Transaction() {
    }

    public Transaction(Long id, Long customerId, Double amount, LocalDate transactionDate) {
        this.id = id;
        this.customerId = customerId;
        this.amount = amount;
        this.transactionDate = transactionDate;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public LocalDate getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDate transactionDate) {
        this.transactionDate = transactionDate;
    }
}
