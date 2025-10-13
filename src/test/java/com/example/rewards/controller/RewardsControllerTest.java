package com.example.rewards.controller;

import com.example.rewards.dto.RewardResponseDto;
import com.example.rewards.service.RewardService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.ArgumentMatchers.*;

@WebMvcTest(controllers = RewardsController.class)
public class RewardsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RewardService rewardService;

    @Test
    public void testGetRewards_callsServiceAndReturnsOk() throws Exception {
        Mockito.when(rewardService.calculateRewards(anyLong(), any(LocalDate.class), any(LocalDate.class)))
                .thenReturn(new RewardResponseDto());

        mockMvc.perform(get("/api/rewards")
                .param("customerId", "1")
                .param("start", "2025-01-01")
                .param("end", "2025-03-31"))
                .andExpect(status().isOk());
    }
}
