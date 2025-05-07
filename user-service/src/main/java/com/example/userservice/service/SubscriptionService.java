package com.example.userservice.service;

import com.example.userservice.dto.SubscriptionDTO;
import com.example.userservice.model.Subscription;
import com.example.userservice.model.User;
import com.example.userservice.repository.SubscriptionRepository;
import com.example.userservice.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SubscriptionService {
    private static final Logger logger = LoggerFactory.getLogger(SubscriptionService.class);

    private final SubscriptionRepository subscriptionRepository;
    private final UserRepository userRepository;

    public SubscriptionService(SubscriptionRepository subscriptionRepository, UserRepository userRepository) {
        this.subscriptionRepository = subscriptionRepository;
        this.userRepository = userRepository;
    }

    public Subscription addSubscription(Long userId, SubscriptionDTO subscriptionDTO) {
        logger.info("Adding subscription for user ID: {}", userId);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with ID: " + userId));

        Subscription subscription = new Subscription();
        subscription.setUser(user);
        subscription.setType(subscriptionDTO.getType());
        subscription.setStartDate(subscriptionDTO.getStartDate());
        subscription.setEndDate(subscriptionDTO.getEndDate());

        Subscription savedSubscription = subscriptionRepository.save(subscription);
        logger.info("Subscription added successfully for user ID: {}", userId);
        return savedSubscription;
    }

    public List<Subscription> getUserSubscriptions(Long userId) {
        logger.info("Fetching subscriptions for user ID: {}", userId);
        if (!userRepository.existsById(userId)) {
            throw new EntityNotFoundException("User not found with ID: " + userId);
        }
        return subscriptionRepository.findByUserId(userId);
    }

    public void deleteSubscription(Long userId, Long subscriptionId) {
        logger.info("Deleting subscription ID: {} for user ID: {}", subscriptionId, userId);
        if (!userRepository.existsById(userId)) {
            throw new EntityNotFoundException("User not found with ID: " + userId);
        }
        Subscription subscription = subscriptionRepository.findById(subscriptionId)
                .orElseThrow(() -> new EntityNotFoundException("Subscription not found with ID: " + subscriptionId));
        if (!subscription.getUser().getId().equals(userId)) {
            throw new IllegalStateException("Subscription ID: " + subscriptionId + " does not belong to user ID: " + userId);
        }
        subscriptionRepository.delete(subscription);
        logger.info("Subscription ID: {} deleted successfully", subscriptionId);
    }

    public List<String> getTopSubscriptions() {
        logger.info("Fetching top 3 subscription types");
        List<Object[]> results = subscriptionRepository.findTopSubscriptions();
        return results.stream()
                .map(result -> (String) result[0])
                .collect(Collectors.toList());
    }
}