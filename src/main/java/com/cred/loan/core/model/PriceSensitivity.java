package com.cred.loan.core.model;

import java.time.LocalDateTime;

/**
 * Interface representing price sensitivity metrics for a user.
 */
public interface PriceSensitivity {
    /**
     * Gets the sensitivity score (0.0 to 1.0).
     * Higher values indicate higher price sensitivity.
     *
     * @return The sensitivity score
     */
    Double getSensitivity();

    /**
     * Gets the confidence level of the sensitivity score (0.0 to 1.0).
     *
     * @return The confidence level
     */
    Double getConfidence();

    /**
     * Gets the timestamp when the sensitivity was calculated.
     *
     * @return The calculation timestamp
     */
    LocalDateTime getCalculatedAt();
} 