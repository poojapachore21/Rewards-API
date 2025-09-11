package com.rewards.reward_api.repository;

public interface JpaRepository<T1, T2> {

    void save(Transaction transaction);

}
