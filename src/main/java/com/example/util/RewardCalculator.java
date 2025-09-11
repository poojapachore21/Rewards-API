package com.rewards.util;

public class RewardCalculator {

    public static int calculatePoints(Transaction transaction) {
        int points = 0;
        double amount = transaction.getAmount();

        if (amount > 100) {
            points += (amount - 100) * 2; // 2 points for every dollar over $100
            points += 50 * 1; // 1 point for every dollar between $50 and $100
        } else if (amount > 50) {
            points += (amount - 50) * 1; // 1 point for every dollar between $50 and $100
        }
        return points;
    }
}
