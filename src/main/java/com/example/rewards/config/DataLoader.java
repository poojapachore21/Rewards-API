package com.example.rewards.config;

import com.example.rewards.model.Customer;
import com.example.rewards.model.Purchase;
import com.example.rewards.repository.CustomerRepository;
import com.example.rewards.repository.PurchaseRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Component
public class DataLoader implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(DataLoader.class);

    private final CustomerRepository customerRepository;
    private final PurchaseRepository purchaseRepository;

    public DataLoader(CustomerRepository customerRepository, PurchaseRepository purchaseRepository) {
        this.customerRepository = customerRepository;
        this.purchaseRepository = purchaseRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        logger.info("Loading sample data...");

        Customer c1 = new Customer("Alex Johnson", "alex@example.com");
        Customer c2 = new Customer("Priya Singh", "priya@example.com");

        // Save customers first
        customerRepository.save(c1);
        customerRepository.save(c2);

        // Alex purchases across months - create Purchase objects before saving
        Purchase p1 = new Purchase(new BigDecimal("120.00"), LocalDateTime.of(2025,1,15,10,15,30));
        p1.setCustomer(c1);
        purchaseRepository.save(p1);

        Purchase p2 = new Purchase(new BigDecimal("75.00"), LocalDateTime.of(2025,2,10,12,0,0));
        p2.setCustomer(c1);
        purchaseRepository.save(p2);

        Purchase p3 = new Purchase(new BigDecimal("200.00"), LocalDateTime.of(2025,2,14,16,30,0));
        p3.setCustomer(c1);
        purchaseRepository.save(p3);

        Purchase p4 = new Purchase(new BigDecimal("50.00"), LocalDateTime.of(2025,3,3,9,0,0));
        p4.setCustomer(c1);
        purchaseRepository.save(p4);

        Purchase p5 = new Purchase(new BigDecimal("110.00"), LocalDateTime.of(2025,3,22,18,45,0));
        p5.setCustomer(c1);
        purchaseRepository.save(p5);

        // Priya purchases
        Purchase p6 = new Purchase(new BigDecimal("130.00"), LocalDateTime.of(2025,2,5,11,11,0));
        p6.setCustomer(c2);
        purchaseRepository.save(p6);

        logger.info("Sample data loaded.");
    }
}