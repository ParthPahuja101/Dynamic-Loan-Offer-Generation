package com.cred.loan.optimization.model;

import com.cred.loan.behavior.model.PriceSensitivity;

/**
 * Represents a user's profile used for loan offer optimization, containing
 * behavior analysis results and risk assessment information.
 */
public class UserProfile {
    private final PriceSensitivity priceSensitivity;
    private final double conversionProbability;
    private final double longTermValue;

    /**
     * Creates a new UserProfile instance.
     *
     * @param priceSensitivity User's price sensitivity information
     * @param conversionProbability Probability of user converting on an offer
     * @param longTermValue Estimated long-term value of the user
     */
    public UserProfile(
            PriceSensitivity priceSensitivity,
            double conversionProbability,
            double longTermValue) {
        validateValues(conversionProbability, longTermValue);
        this.priceSensitivity = priceSensitivity;
        this.conversionProbability = conversionProbability;
        this.longTermValue = longTermValue;
    }

    /**
     * Gets the user's price sensitivity information.
     *
     * @return Price sensitivity
     */
    public PriceSensitivity getPriceSensitivity() {
        return priceSensitivity;
    }

    /**
     * Gets the probability of the user converting on an offer.
     *
     * @return Conversion probability (0.0 to 1.0)
     */
    public double getConversionProbability() {
        return conversionProbability;
    }

    /**
     * Gets the estimated long-term value of the user.
     *
     * @return Long-term value
     */
    public double getLongTermValue() {
        return longTermValue;
    }

    /**
     * Validates the profile values.
     *
     * @param conversionProbability Probability of conversion
     * @param longTermValue Long-term value
     * @throws IllegalArgumentException if values are invalid
     */
    private void validateValues(double conversionProbability, double longTermValue) {
        if (conversionProbability < 0 || conversionProbability > 1) {
            throw new IllegalArgumentException(
                "Conversion probability must be between 0 and 1"
            );
        }

        if (longTermValue < 0) {
            throw new IllegalArgumentException(
                "Long-term value cannot be negative"
            );
        }
    }

    @Override
    public String toString() {
        return String.format(
            "UserProfile{priceSensitivity=%s, conversionProbability=%.2f, longTermValue=%.2f}",
            priceSensitivity, conversionProbability, longTermValue
        );
    }
} 