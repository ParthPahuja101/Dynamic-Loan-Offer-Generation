package com.cred.loan.behavior.model;

import java.time.LocalDateTime;

/**
 * Represents a user's price sensitivity score and confidence level.
 * This class is used to determine how a user might react to different price points
 * in loan offers.
 */
public class PriceSensitivity {
    private final double sensitivity;
    private final double confidence;
    private final LocalDateTime calculatedAt;

    /**
     * Creates a new PriceSensitivity instance.
     *
     * @param sensitivity The price sensitivity score (0.0 to 1.0)
     * @param confidence The confidence level in the sensitivity score (0.0 to 1.0)
     * @param calculatedAt The timestamp when the sensitivity was calculated
     * @throws IllegalArgumentException if sensitivity or confidence is not between 0 and 1
     */
    public PriceSensitivity(double sensitivity, double confidence, LocalDateTime calculatedAt) {
        validateScore(sensitivity, "sensitivity");
        validateScore(confidence, "confidence");
        
        this.sensitivity = sensitivity;
        this.confidence = confidence;
        this.calculatedAt = calculatedAt;
    }

    /**
     * Gets the price sensitivity score.
     * Higher values indicate higher price sensitivity.
     *
     * @return The sensitivity score (0.0 to 1.0)
     */
    public double getSensitivity() {
        return sensitivity;
    }

    /**
     * Gets the confidence level in the sensitivity score.
     * Higher values indicate higher confidence in the sensitivity assessment.
     *
     * @return The confidence level (0.0 to 1.0)
     */
    public double getConfidence() {
        return confidence;
    }

    /**
     * Gets the timestamp when the sensitivity was calculated.
     *
     * @return The calculation timestamp
     */
    public LocalDateTime getCalculatedAt() {
        return calculatedAt;
    }

    /**
     * Determines if the user is highly price sensitive.
     *
     * @return true if sensitivity > 0.7 and confidence > 0.6
     */
    public boolean isHighlyPriceSensitive() {
        return sensitivity > 0.7 && confidence > 0.6;
    }

    /**
     * Determines if the user is moderately price sensitive.
     *
     * @return true if 0.3 <= sensitivity <= 0.7 and confidence > 0.6
     */
    public boolean isModeratelyPriceSensitive() {
        return sensitivity >= 0.3 && sensitivity <= 0.7 && confidence > 0.6;
    }

    /**
     * Determines if the user is price insensitive.
     *
     * @return true if sensitivity < 0.3 and confidence > 0.6
     */
    public boolean isPriceInsensitive() {
        return sensitivity < 0.3 && confidence > 0.6;
    }

    private void validateScore(double score, String name) {
        if (score < 0 || score > 1) {
            throw new IllegalArgumentException(name + " must be between 0 and 1");
        }
    }
} 