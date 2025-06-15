package com.cred.loan.data.service.impl;

import com.cred.loan.core.model.*;
import com.cred.loan.data.service.DataService;
import com.cred.loan.data.repository.UserRepository;
import com.cred.loan.data.repository.BehaviorRepository;
import com.cred.loan.data.repository.RiskRepository;
import com.cred.loan.data.repository.OfferRepository;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Implementation of the DataService interface that handles data access operations.
 */
public class DataServiceImpl implements DataService {
    private final UserRepository userRepository;
    private final BehaviorRepository behaviorRepository;
    private final RiskRepository riskRepository;
    private final OfferRepository offerRepository;
    private final ExecutorService executorService;

    public DataServiceImpl(
            UserRepository userRepository,
            BehaviorRepository behaviorRepository,
            RiskRepository riskRepository,
            OfferRepository offerRepository) {
        this.userRepository = userRepository;
        this.behaviorRepository = behaviorRepository;
        this.riskRepository = riskRepository;
        this.offerRepository = offerRepository;
        this.executorService = Executors.newFixedThreadPool(4);
    }

    @Override
    public CompletableFuture<UserData> getUserData(String userId) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                return userRepository.findById(userId)
                        .orElseThrow(() -> new RuntimeException("User not found: " + userId));
            } catch (Exception e) {
                throw new RuntimeException("Error fetching user data: " + e.getMessage(), e);
            }
        }, executorService);
    }

    @Override
    public CompletableFuture<BehaviorData> getBehaviorData(String userId) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                return behaviorRepository.findByUserId(userId)
                        .orElseThrow(() -> new RuntimeException("Behavior data not found for user: " + userId));
            } catch (Exception e) {
                throw new RuntimeException("Error fetching behavior data: " + e.getMessage(), e);
            }
        }, executorService);
    }

    @Override
    public CompletableFuture<RiskData> getRiskData(String userId) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                return riskRepository.findByUserId(userId)
                        .orElseThrow(() -> new RuntimeException("Risk data not found for user: " + userId));
            } catch (Exception e) {
                throw new RuntimeException("Error fetching risk data: " + e.getMessage(), e);
            }
        }, executorService);
    }

    @Override
    public CompletableFuture<Void> saveOfferData(OfferData offerData) {
        return CompletableFuture.runAsync(() -> {
            try {
                offerRepository.save(offerData);
            } catch (Exception e) {
                throw new RuntimeException("Error saving offer data: " + e.getMessage(), e);
            }
        }, executorService);
    }

    /**
     * Shuts down the executor service when the service is no longer needed.
     */
    public void shutdown() {
        executorService.shutdown();
    }
} 