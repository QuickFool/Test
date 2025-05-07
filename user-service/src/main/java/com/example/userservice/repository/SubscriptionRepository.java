package com.example.userservice.repository;

import com.example.userservice.model.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
    List<Subscription> findByUserId(Long userId);

    @Query("SELECT s.type, COUNT(s) as count FROM Subscription s GROUP BY s.type ORDER BY count DESC LIMIT 3")
    List<Object[]> findTopSubscriptions();
}