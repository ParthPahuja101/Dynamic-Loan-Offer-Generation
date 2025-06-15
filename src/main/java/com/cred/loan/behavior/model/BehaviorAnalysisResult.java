package com.cred.loan.behavior.model;

import java.time.LocalDateTime;

/**
 * Represents the complete result of a user behavior analysis, including
 * price sensitivity, conversion probability, and long-term value assessment.
 */
public class BehaviorAnalysisResult {
    private final PriceSensitivity priceSensitivity;
    private final double conversionProbability;
    private final double longTermValue;
    private final LocalDateTime analyzedAt;

    /**
     * Creates a new BehaviorAnalysisResult instance.
     *
     * @param priceSensitivity The user's price sensitivity assessment
     * @param conversionProbability The probability of offer conversion (0.0 to 1.0)
     * @param longTermValue The estimated long-term value of the user (0.0 to 1.0)
     * @param analyzedAt The timestamp when the analysis was performed
     * @throws IllegalArgumentException if probabilities are not between 0 and 1
     */
    public BehaviorAnalysisResult(
            PriceSensitivity priceSensitivity,
            double conversionProbability,
            double longTermValue,
            LocalDateTime analyzedAt) {
        validateProbability(conversionProbability, "conversion probability");
        validateProbability(longTermValue, "long-term value");
        
        this.priceSensitivity = priceSensitivity;
        this.conversionProbability = conversionProbability;
        this.longTermValue = longTermValue;
        this.analyzedAt = analyzedAt;
    }

    /**
     * Gets the user's price sensitivity assessment.
     *
     * @return The price sensitivity object
     */
    public PriceSensitivity getPriceSensitivity() {
        return priceSensitivity;
    }

    /**
     * Gets the probability of the user converting on an offer.
     *
     * @return The conversion probability (0.0 to 1.0)
     */
    public double getConversionProbability() {
        return conversionProbability;
    }

    /**
     * Gets the estimated long-term value of the user.
     *
     * @return The long-term value score (0.0 to 1.0)
     */
    public double getLongTermValue() {
        return longTermValue;
    }

    /**
     * Gets the timestamp when the analysis was performed.
     *
     * @return The analysis timestamp
     */
    public LocalDateTime getAnalyzedAt() {
        return analyzedAt;
    }

    /**
     * Determines if the user has a high likelihood of conversion.
     *
     * @return true if conversion probability > 0.7
     */
    public boolean hasHighConversionProbability() {
        return conversionProbability > 0.7;
    }

    /**
     * Determines if the user has high long-term value.
     *
     * @return true if long-term value > 0.7
     */
    public boolean hasHighLongTermValue() {
        return longTermValue > 0.7;
    }

    /**
     * Calculates the overall user value score.
     * This is a weighted combination of conversion probability and long-term value.
     *
     * @return The overall value score (0.0 to 1.0)
     */
    public double calculateOverallValue() {
        // Weight conversion probability more heavily for immediate value
        return (conversionProbability * 0.6) + (longTermValue * 0.4);
    }

    private void validateProbability(double probability, String name) {
        if (probability < 0 || probability > 1) {
            throw new IllegalArgumentException(name + " must be between 0 and 1");
        }
    }
} 