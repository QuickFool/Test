package com.example.userservice.controller;

import com.example.userservice.model.Subscription;
import com.example.userservice.service.SubscriptionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/subscriptions")
public class SubscriptionController {
    private static final Logger logger = LoggerFactory.getLogger(SubscriptionController.class);

    private final SubscriptionService subscriptionService;

    public SubscriptionController(SubscriptionService subscriptionService) {
        this.subscriptionService = subscriptionService;
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Subscription>> getUserSubscriptions(@PathVariable Long userId) {
        logger.info("Fetching subscriptions for user ID: {}", userId);
        List<Subscription> subscriptions = subscriptionService.getUserSubscriptions(userId);
        return ResponseEntity.ok(subscriptions);
    }

    @DeleteMapping("/user/{userId}/{subscriptionId}")
    public ResponseEntity<Void> deleteSubscription(@PathVariable Long userId, @PathVariable Long subscriptionId) {
        logger.info("Deleting subscription ID: {} for user ID: {}", subscriptionId, userId);
        subscriptionService.deleteSubscription(userId, subscriptionId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/top")
    public ResponseEntity<List<String>> getTopSubscriptions() {
        logger.info("Fetching top 3 subscription types");
        List<String> topSubscriptions = subscriptionService.getTopSubscriptions();
        return ResponseEntity.ok(topSubscriptions);
    }
}