package com.example.rewards.service;

import com.example.rewards.dto.RewardResponseDto;
import com.example.rewards.exception.ResourceNotFoundException;
import com.example.rewards.model.Customer;
import com.example.rewards.model.Purchase;
import com.example.rewards.repository.CustomerRepository;
import com.example.rewards.repository.PurchaseRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class RewardService {

    private static final Logger logger = LoggerFactory.getLogger(RewardService.class);

    private final CustomerRepository customerRepository;
    private final PurchaseRepository purchaseRepository;

    public RewardService(CustomerRepository customerRepository, PurchaseRepository purchaseRepository) {
        this.customerRepository = customerRepository;
        this.purchaseRepository = purchaseRepository;
    }

    /**
     * Public API: calculate rewards for a customer between start and end (inclusive).
     */
    @Transactional(readOnly = true)
    public RewardResponseDto calculateRewards(Long customerId, LocalDate startDate, LocalDate endDate) {
        logger.info("Received request to calculate rewards for customerId: {}, start: {}, end: {}", customerId, startDate, endDate);

        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id " + customerId));

        if (endDate.isBefore(startDate)) {
            throw new IllegalArgumentException("End date must be the same or after start date");
        }

        // Convert to start & end LocalDateTime to include full days (inclusive)
        LocalDateTime startDateTime = startDate.atStartOfDay();
        LocalDateTime endDateTime = endDate.atTime(23, 59, 59);

    try {
        List<Purchase> purchaseList = purchaseRepository.findByCustomerIdAndPurchaseDateBetween(customerId, startDateTime, endDateTime);

        List<com.example.rewards.dto.TransactionResponse> transactionResponses = purchaseList.stream()
            .map(purchase -> new com.example.rewards.dto.TransactionResponse(
                purchase.getId(),
                purchase.getAmount() != null ? purchase.getAmount().doubleValue() : 0.0,
                purchase.getPurchaseDate().toLocalDate().toString()))
            .collect(Collectors.toList());

        Map<java.time.YearMonth, Long> pointsByMonth = new HashMap<>();
        Map<java.time.YearMonth, java.math.BigDecimal> amountByMonth = new HashMap<>();

            for (Purchase purchase : purchaseList) {
                long points = calculatePoints(purchase.getAmount());
                java.time.YearMonth yearMonth = java.time.YearMonth.from(purchase.getPurchaseDate().toLocalDate());
                pointsByMonth.merge(yearMonth, points, Long::sum);
                BigDecimal amount = purchase.getAmount() != null ? purchase.getAmount() : BigDecimal.ZERO;
                amountByMonth.merge(yearMonth, amount, java.math.BigDecimal::add);
            }

        Map<String, com.example.rewards.dto.MonthlyReward> monthlyRewards = pointsByMonth.entrySet().stream()
            .sorted(Map.Entry.comparingByKey())
            .collect(Collectors.toMap(
                entry -> entry.getKey().getMonth().name(),
                entry -> new com.example.rewards.dto.MonthlyReward(
                    entry.getValue(),
                    amountByMonth.getOrDefault(entry.getKey(), java.math.BigDecimal.ZERO).doubleValue()),
                (existing, replacement) -> existing,
                java.util.LinkedHashMap::new
            ));
        return new RewardResponseDto(customer.getId(), customer.getName(), monthlyRewards, transactionResponses);
    } catch (Exception ex) {
        logger.error("Error while fetching purchases: {}", ex.getMessage(), ex);
        throw new RuntimeException("Failed to fetch purchases", ex);
    }
    }

    /**
     * Reward logic:
     *  - 2 points for every dollar spent over 100
     *  - 1 point for every dollar spent between 50 and 100 (inclusive of 100)
     *
     * Amounts are rounded down to whole dollars since points are per dollar.
     */
    private long calculatePoints(BigDecimal amount) {
        if (amount == null) return 0L;
        long dollars = amount.setScale(0, RoundingMode.DOWN).longValue();
        long points = 0;
        if (dollars > 100) {
            points += 2L * (dollars - 100);
            points += 1L * 50; // 50 dollars between 51..100 inclusive
        } else if (dollars > 50) {
            points += 1L * (dollars - 50);
        }
        return points;
    }
}
