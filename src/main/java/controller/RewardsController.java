package com.example.rewards.controller;

import com.example.rewards.model.RewardResponse;
import com.example.rewards.service.RewardService;
import java.time.LocalDate;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/rewards")
public class RewardsController {

    private final RewardService rewardService;

    public RewardsController(RewardService rewardService) {
        this.rewardService = rewardService;
    }

    // ✅ Endpoint 1: Monthly Rewards
    @GetMapping("/monthly/{customerId}")
    public ResponseEntity<RewardResponse> getMonthlyRewards(
            @PathVariable String customerId,
            @RequestParam("start") String startDate,
            @RequestParam("end") String endDate) {

        LocalDate start = LocalDate.parse(startDate);
        LocalDate end = LocalDate.parse(endDate);

        RewardResponse response = rewardService.calculateMonthlyRewards(customerId, start, end);
        return ResponseEntity.ok(response);
    }

    // ✅ Endpoint 2: Total Rewards
    @GetMapping("/total/{customerId}")
    public ResponseEntity<Long> getTotalRewards(
            @PathVariable String customerId,
            @RequestParam("start") String startDate,
            @RequestParam("end") String endDate) {

        LocalDate start = LocalDate.parse(startDate);
        LocalDate end = LocalDate.parse(endDate);

        long totalPoints = rewardService.calculateTotalPoints(customerId, start, end);
        return ResponseEntity.ok(totalPoints);
    }
}
