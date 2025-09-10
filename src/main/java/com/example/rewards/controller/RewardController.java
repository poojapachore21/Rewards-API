package com.example.rewards.controller;

import com.example.rewards.service.RewardService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/rewards")
public class RewardController {

    private final RewardService rewardService;

    // Add this constructor for dependency injection
    public RewardController(RewardService rewardService) {
        this.rewardService = rewardService;
    }

    @GetMapping("/monthly/{customerId}")
    public ResponseEntity<RewardResponse>
            @PathVariable String customerId,

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCustomerId() {
        return customerId;
    }

    @RequestParam String startDate,
    @RequestParam String endDate)

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate start = LocalDate.parse(startDate, formatter);
        LocalDate end = LocalDate.parse(endDate, formatter);

        com.rewards.controller.RewardResponse response = rewardService.calculateMonthlyRewards(customerId, start, end);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/total/{customerId}")
    public ResponseEntity<Long> getTotalRewards(
            @PathVariable String customerId,
            @RequestParam String startDate,
            @RequestParam String endDate) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate start = LocalDate.parse(startDate, formatter);
        LocalDate end = LocalDate.parse(endDate, formatter);

        long totalPoints = rewardService.calculateTotalPoints(customerId, start, end);
        return ResponseEntity.ok(totalPoints);
    }

    public ResponseEntity<RewardResponse> getMonthlyRewards(String string, String string2, String string3) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getMonthlyRewards'");
    }
}
