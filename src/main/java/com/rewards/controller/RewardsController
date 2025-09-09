package com.example.rewards.controller;

import com.example.rewards.dto.RewardResponseDTO;
import com.example.rewards.service.RewardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/rewards")
public class RewardsController {

    @Autowired
    private RewardService rewardService;

    @GetMapping
    public ResponseEntity<RewardResponseDTO> getRewards(
            @RequestParam Long customerId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end) {

        RewardResponseDTO response = rewardService.calculateRewards(customerId, start, end);
        return ResponseEntity.ok(response);
    }
}
