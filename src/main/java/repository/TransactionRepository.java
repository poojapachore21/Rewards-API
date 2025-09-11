package com.example.rewards.repository;

import com.example.rewards.model.Transaction;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class TransactionRepository {

    private final List<Transaction> transactions = Arrays.asList(
            new Transaction(1L, 101L, 120, LocalDate.of(2025, 6, 10)),
            new Transaction(2L, 101L, 75, LocalDate.of(2025, 7, 12)),
            new Transaction(3L, 102L, 200, LocalDate.of(2025, 7, 14)),
            new Transaction(4L, 101L, 30, LocalDate.of(2025, 8, 9)));

    public List<Transaction> findByCustomerAndDateRange(String customerId, LocalDate start, LocalDate end) {
        Long custId = Long.valueOf(customerId);

        return transactions.stream()
                .filter(t -> t.getCustomerId().equals(custId))
                .filter(t -> !t.getDate().isBefore(start) && !t.getDate().isAfter(end))
                .collect(Collectors.toList());
    }
}
