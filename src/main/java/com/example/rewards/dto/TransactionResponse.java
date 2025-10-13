package com.example.rewards.dto;

public class TransactionResponse {
    private Long id;
    private double amount;
    private String date;

    public TransactionResponse() {}

    public TransactionResponse(Long id, double amount, String date) {
        this.id = id;
        this.amount = amount;
        this.date = date;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }
    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }
}
