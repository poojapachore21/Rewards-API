package com.example.rewards.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class PurchaseDto {
    private Long id;
    private BigDecimal amount;
    private LocalDateTime purchaseDate;

    public PurchaseDto() {}

    public PurchaseDto(Long id, BigDecimal amount, LocalDateTime purchaseDate) {
        this.id = id;
        this.amount = amount;
        this.purchaseDate = purchaseDate;
    }

    // getters & setters
    public Long getId() { return id; }
    public BigDecimal getAmount() { return amount; }
    public LocalDateTime getPurchaseDate() { return purchaseDate; }
    public void setId(Long id) { this.id = id; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }
    public void setPurchaseDate(LocalDateTime purchaseDate) { this.purchaseDate = purchaseDate; }
}
