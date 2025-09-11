package com.example.rewards.service;

import com.example.rewards.dto.RewardResponseDTO;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.*;

@Service
public class RewardService {

    // Simulated transaction data
    private List<Transaction> getAllTransactions() {
        return Arrays.asList(
                new Transaction(1L, 101L, 120, LocalDate.of(2025, 6, 10)),
                new Transaction(2L, 101L, 75, LocalDate.of(2025, 7, 12)),
                new Transaction(3L, 102L, 200, LocalDate.of(2025, 7, 14)),
                new Transaction(4L, 101L, 30, LocalDate.of(2025, 8, 9)));
    }

    @SuppressWarnings("unchecked")
    public RewardResponseDTO calculateRewards(Long customerId, LocalDate start, LocalDate end) {
        List<Transaction> all = getAllTransactions();
        Map<YearMonth, Integer> monthlyPoints = new HashMap<>();
        int totalPoints = 0;
        List<com.example.rewards.dto.Transaction> customerTxns = new ArrayList<>();

        for (Transaction txn : all) {
            if (txn.getCustomerId().equals(customerId) &&
                    !txn.getDate().isBefore(start) &&
                    !txn.getDate().isAfter(end)) {

                int points = RewardCalculator.calculatePoints(txn.getAmount());
                YearMonth ym = YearMonth.from(txn.getDate());

                monthlyPoints.put(ym, monthlyPoints.getOrDefault(ym, 0) + points);
                totalPoints += points;
                customerTxns.addAll((Collection<? extends com.example.rewards.dto.Transaction>) txn);
            }
        }

        return new RewardResponseDTO(customerId, monthlyPoints, totalPoints, customerTxns);
    }
}
