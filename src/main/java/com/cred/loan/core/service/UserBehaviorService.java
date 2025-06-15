package com.cred.loan.core.service;

import com.cred.loan.core.model.UserData;
import com.cred.loan.core.model.BehaviorAnalysisResult;

import java.util.concurrent.CompletableFuture;

/**
 * Service interface for analyzing user behavior.
 * This service evaluates user behavior patterns to optimize loan offers
 * and predict conversion probabilities.
 */
public interface UserBehaviorService {
    /**
     * Analyzes user behavior to determine optimal offer parameters.
     *
     * @param userData The user data to analyze
     * @return A CompletableFuture containing the behavior analysis result
     */
    CompletableFuture<BehaviorAnalysisResult> analyzeBehavior(UserData userData);

    /**
     * Calculates the price sensitivity of a user.
     *
     * @param userData The user data to calculate price sensitivity for
     * @return A CompletableFuture containing the price sensitivity score
     */
    CompletableFuture<Double> calculatePriceSensitivity(UserData userData);

    /**
     * Calculates the probability of offer acceptance.
     *
     * @param userData The user data to calculate conversion probability for
     * @return A CompletableFuture containing the conversion probability
     */
    CompletableFuture<Double> calculateConversionProbability(UserData userData);
} 