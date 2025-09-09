package com.rewards.reward_api.repository;

import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    // Method to find transactions for a customer within a date range
    List<Transaction> findByCustomerIdAndDateBetween(Long customerId, LocalDate start, LocalDate end);
}
