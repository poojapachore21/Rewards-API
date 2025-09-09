package com.rewards.reward_api.repository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    void save(com.rewards.reward_api.Customer customer);
}
