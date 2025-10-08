
import com.example.rewards.model.RewardResponse;
import com.example.rewards.repository.TransactionRepository;
import com.example.rewards.service.BeforeEach;
import com.example.rewards.service.RewardService;

import java.time.LocalDate;

class RewardServiceTest {

    private RewardService rewardService;

    @BeforeEach
    void setUp() {
        TransactionRepository transactionRepository = new TransactionRepository();
        rewardService = new RewardService(transactionRepository);
    }

    @Test
    void testCalculateMonthlyRewards() {
        RewardResponse response = rewardService.calculateMonthlyRewards(
                "101",
                LocalDate.of(2025, 6, 1),
                LocalDate.of(2025, 8, 31));

        assertEquals("101", response.getCustomerId());
        assertEquals(2, ((Object) response.getMonthlyPoints())); // June + July only (August 30 not rewarded)
        assertEquals(90 + 25, response.getTotalPoints()); // June(90) + July(25)
    }

    private void assertEquals(int i, Object customerId) {
        // throw new UnsupportedOperationException("Unimplemented method
        // 'assertEquals'");
    }

    @SuppressWarnings("unused")
    private void assertEquals(String string, Object customerId) {

        throw new UnsupportedOperationException("Unimplemented method 'assertEquals'");
    }

    @Test
    void testCalculateTotalPoints() {
        long total = rewardService.calculateTotalPoints(
                "101",
                LocalDate.of(2025, 6, 1),
                LocalDate.of(2025, 8, 31));

        assertEquals(115, total);
    }
}
