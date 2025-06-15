package com.cred.loan.core.model;

import java.time.LocalDateTime;

/**
 * Interface representing the result of user behavior analysis.
 * Contains insights about user behavior patterns and preferences.
 */
public interface BehaviorAnalysisResult {
    /**
     * Gets the price sensitivity metrics for the user.
     *
     * @return The price sensitivity metrics
     */
    PriceSensitivity getPriceSensitivity();

    /**
     * Gets the probability of the user converting (0.0 to 1.0).
     *
     * @return The conversion probability
     */
    Double getConversionProbability();

    /**
     * Gets the long-term value score for the user (0.0 to 1.0).
     *
     * @return The long-term value score
     */
    Double getLongTermValue();

    /**
     * Gets the timestamp when the analysis was performed.
     *
     * @return The analysis timestamp
     */
    LocalDateTime getAnalyzedAt();
} 