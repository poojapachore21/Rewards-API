package com.example.rewards.service;

import com.example.rewards.dto.CustomerDto;
import com.example.rewards.dto.PurchaseDto;
import com.example.rewards.dto.RewardResponseDto;
import com.example.rewards.exception.ResourceNotFoundException;
import com.example.rewards.model.Customer;
import com.example.rewards.model.Purchase;
import com.example.rewards.repository.CustomerRepository;
import com.example.rewards.repository.PurchaseRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.CompletableFuture;
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

        // Fetch purchases asynchronously to simulate async API call
        try {
            List<Purchase> purchases = fetchPurchasesAsync(customerId, startDateTime, endDateTime).get();
            // map to DTOs for response
            List<PurchaseDto> purchaseDtos = purchases.stream()
                    .map(p -> new PurchaseDto(p.getId(), p.getAmount(), p.getPurchaseDate()))
                    .collect(Collectors.toList());

            // Group by YearMonth
            Map<YearMonth, Long> byMonth = new HashMap<>();
            long totalPoints = 0L;
            for (Purchase p : purchases) {
                long pts = calculatePoints(p.getAmount());
                YearMonth ym = YearMonth.from(p.getPurchaseDate());
                byMonth.merge(ym, pts, Long::sum);
                totalPoints += pts;
            }

            // Convert YearMonth keys to yyyy-MM strings
            Map<String, Long> monthlyPoints = byMonth.entrySet().stream()
                    .sorted(Map.Entry.comparingByKey())
                    .collect(Collectors.toMap(
                            e -> e.getKey().toString(),
                            Map.Entry::getValue,
                            (a,b) -> a,
                            LinkedHashMap::new
                    ));

            CustomerDto customerDto = new CustomerDto(customer.getId(), customer.getName(), customer.getEmail());
            return new RewardResponseDto(customerDto, monthlyPoints, totalPoints, purchaseDtos);

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

    /**
     * Simulate asynchronous call to fetch purchase data (e.g. from remote service or repository).
     * Using supplyAsync to intentionally run in another thread.
     */
    @Async
    public CompletableFuture<List<Purchase>> fetchPurchasesAsync(Long customerId, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        logger.info("Fetching purchases asynchronously for customerId: {} between {} and {}", customerId, startDateTime, endDateTime);
        return CompletableFuture.supplyAsync(() -> purchaseRepository.findByCustomerIdAndPurchaseDateBetween(customerId, startDateTime, endDateTime));
    }
}
