package com.example.rewards.controller;

import com.example.rewards.dto.RewardResponseDto;
import com.example.rewards.service.RewardService;
import jakarta.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api")
@Validated
public class RewardsController {

    private static final Logger logger = LoggerFactory.getLogger(RewardsController.class);

    private final RewardService rewardService;

    public RewardsController(RewardService rewardService) {
        this.rewardService = rewardService;
    }

    /* Endpoint */
     
    @GetMapping("/rewards")
    public ResponseEntity<RewardResponseDto> getRewards(
            @RequestParam @NotNull Long customerId,
            @RequestParam @NotNull @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
            @RequestParam @NotNull @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end) {

        logger.info("Received Request to calculate rewards for customer ID: {}", customerId);
        RewardResponseDto response = rewardService.calculateRewards(customerId, start, end);
        return ResponseEntity.ok(response);
    }
}
