package rewards.service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.*;

import org.springframework.stereotype.Service;

import controller.RewardResponseDTO;
import example.rewards.model.Transaction;
import repository.TransactionRepository;
import util.RewardCalculator;

@Service
public class RewardService {

    public RewardService(TransactionRepository transactionRepository) {
        // TODO Auto-generated constructor stub
    }

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
        List<example.rewards.model.Transaction> customerTxns = new ArrayList<>();

        for (Transaction txn : all) {
            if (txn.getCustomerId().equals(customerId) &&
                    !txn.getDate().isBefore(start) &&
                    !txn.getDate().isAfter(end)) {

                int points = RewardCalculator.calculatePoints(txn.getAmount());
                YearMonth ym = YearMonth.from(txn.getDate());

                monthlyPoints.put(ym, monthlyPoints.getOrDefault(ym, 0) + points);
                totalPoints += points;
                customerTxns.addAll((Collection<? extends example.rewards.model.Transaction>) txn);
            }
        }

        return new RewardResponseDTO(null, null, totalPoints);
    }

    public RewardResponse calculateMonthlyRewards(String customerId, LocalDate start, LocalDate end) {
        // throw new UnsupportedOperationException("Unimplemented method
        // 'calculateMonthlyRewards'");
    }

    public long calculateTotalPoints(String customerId, LocalDate start, LocalDate end) {
        // throw new UnsupportedOperationException("Unimplemented method
        // 'calculateTotalPoints'");
    }
}
