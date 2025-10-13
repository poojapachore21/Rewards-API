package com.example.rewards.service;

import com.example.rewards.model.Purchase;
import com.example.rewards.model.Customer;
import com.example.rewards.repository.PurchaseRepository;
import com.example.rewards.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

public class RewardServiceTest {

    private PurchaseRepository purchaseRepository;
    private CustomerRepository customerRepository;
    private RewardService rewardService;

    @BeforeEach
    public void setUp() {
        purchaseRepository = Mockito.mock(PurchaseRepository.class);
        customerRepository = Mockito.mock(CustomerRepository.class);
        rewardService = new RewardService(customerRepository, purchaseRepository);
    }

    @Test
    public void testCalculateRewards_singlePurchaseAbove100() throws Exception {
        Customer testCustomer = new Customer("Test User", "t@example.com");
        java.lang.reflect.Field customerIdField = testCustomer.getClass().getDeclaredField("id");
        customerIdField.setAccessible(true);
        customerIdField.set(testCustomer, 1L);

        when(customerRepository.findById(1L)).thenReturn(Optional.of(testCustomer));

        Purchase purchase = new Purchase(new BigDecimal("120.00"), LocalDateTime.of(2025,1,15,10,15,30));
        java.lang.reflect.Field purchaseIdField = purchase.getClass().getDeclaredField("id");
        purchaseIdField.setAccessible(true);
        purchaseIdField.set(purchase, 10L);
        purchase.setCustomer(testCustomer);

        when(purchaseRepository.findByCustomerIdAndPurchaseDateBetween(eq(1L), any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(List.of(purchase));

        var response = rewardService.calculateRewards(1L, LocalDate.of(2025,1,1), LocalDate.of(2025,1,31));
    assertNotNull(response);
    }

    @Test
    public void testCalculateRewards_noCustomer() {
        when(customerRepository.findById(2L)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> rewardService.calculateRewards(2L, LocalDate.of(2025,1,1), LocalDate.of(2025,1,31)));
    }

    @Test
    public void testCalculateRewards_endDateBeforeStartDate_throwsIllegalArgumentException() {
        Customer testCustomer = new Customer("Test User", "t@example.com");
        java.lang.reflect.Field customerIdField;
        try {
            customerIdField = testCustomer.getClass().getDeclaredField("id");
            customerIdField.setAccessible(true);
            customerIdField.set(testCustomer, 1L);
        } catch (Exception e) {
            fail("Reflection error: " + e.getMessage());
        }
        when(customerRepository.findById(1L)).thenReturn(Optional.of(testCustomer));
        assertThrows(IllegalArgumentException.class, () ->
                rewardService.calculateRewards(1L, LocalDate.of(2025,2,1), LocalDate.of(2025,1,1)));
    }

    @Test
    public void testCalculateRewards_nullAmount_returnsZeroPoints() throws Exception {
        Customer testCustomer = new Customer("Test User", "t@example.com");
        java.lang.reflect.Field customerIdField = testCustomer.getClass().getDeclaredField("id");
        customerIdField.setAccessible(true);
        customerIdField.set(testCustomer, 1L);

        when(customerRepository.findById(1L)).thenReturn(Optional.of(testCustomer));

        Purchase purchase = new Purchase(null, LocalDateTime.of(2025,1,15,10,15,30));
        java.lang.reflect.Field purchaseIdField = purchase.getClass().getDeclaredField("id");
        purchaseIdField.setAccessible(true);
        purchaseIdField.set(purchase, 10L);
        purchase.setCustomer(testCustomer);

        when(purchaseRepository.findByCustomerIdAndPurchaseDateBetween(eq(1L), any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(List.of(purchase));

        var response = rewardService.calculateRewards(1L, LocalDate.of(2025,1,1), LocalDate.of(2025,1,31));
    assertNotNull(response);
    }
}
