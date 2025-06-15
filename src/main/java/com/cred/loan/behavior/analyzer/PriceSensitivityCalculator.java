package com.cred.loan.behavior.analyzer;

import com.cred.loan.core.model.UserData;
import com.cred.loan.behavior.model.PriceSensitivity;

import java.time.LocalDateTime;
import java.util.concurrent.CompletableFuture;

/**
 * Calculates a user's price sensitivity based on various factors including
 * income, existing debt, and behavior patterns.
 */
public class PriceSensitivityCalculator {
    private final double baseSensitivity;
    private final double maxAdjustment;

    /**
     * Creates a new PriceSensitivityCalculator instance.
     *
     * @param baseSensitivity Base sensitivity value (0.0 to 1.0)
     * @param maxAdjustment Maximum adjustment to base sensitivity (0.0 to 1.0)
     * @throws IllegalArgumentException if values are not between 0 and 1
     */
    public PriceSensitivityCalculator(double baseSensitivity, double maxAdjustment) {
        validateValue(baseSensitivity, "base sensitivity");
        validateValue(maxAdjustment, "max adjustment");
        
        this.baseSensitivity = baseSensitivity;
        this.maxAdjustment = maxAdjustment;
    }

    /**
     * Calculates the price sensitivity for a user.
     *
     * @param userData The user data to evaluate
     * @return A CompletableFuture containing the calculated PriceSensitivity
     */
    public CompletableFuture<PriceSensitivity> calculateSensitivity(UserData userData) {
        return CompletableFuture.supplyAsync(() -> {
            double sensitivity = calculateBaseSensitivity(userData);
            double confidence = calculateConfidence(userData);

            return new PriceSensitivity(sensitivity, confidence, LocalDateTime.now());
        });
    }

    /**
     * Calculates the base sensitivity score based on user data.
     *
     * @param userData The user data to evaluate
     * @return The base sensitivity score (0.0 to 1.0)
     */
    private double calculateBaseSensitivity(UserData userData) {
        double incomeFactor = calculateIncomeFactor(userData);
        double debtFactor = calculateDebtFactor(userData);
        double ageFactor = calculateAgeFactor(userData);

        // Weight the factors
        double weightedScore = (incomeFactor * 0.4) +
                             (debtFactor * 0.4) +
                             (ageFactor * 0.2);

        // Apply base sensitivity and adjustments
        return Math.min(1.0, Math.max(0.0,
            baseSensitivity + (weightedScore * maxAdjustment)));
    }

    /**
     * Calculates the confidence level in the sensitivity assessment.
     *
     * @param userData The user data to evaluate
     * @return The confidence level (0.0 to 1.0)
     */
    private double calculateConfidence(UserData userData) {
        int dataPoints = 0;
        double totalConfidence = 0.0;

        if (userData.getIncome() != null) {
            dataPoints++;
            totalConfidence += 1.0;
        }

        if (userData.getExistingDebt() != null) {
            dataPoints++;
            totalConfidence += 1.0;
        }

        if (userData.getAge() != null) {
            dataPoints++;
            totalConfidence += 1.0;
        }

        if (userData.getEmploymentStatus() != null) {
            dataPoints++;
            totalConfidence += 0.8;
        }

        return dataPoints > 0 ? totalConfidence / dataPoints : 0.5;
    }

    private double calculateIncomeFactor(UserData userData) {
        if (userData.getIncome() == null) {
            return 0.5; // Default to medium sensitivity if income is unknown
        }

        // Higher income generally means lower price sensitivity
        double income = userData.getIncome();
        if (income > 200000) return 0.2;      // Very low sensitivity
        if (income > 100000) return 0.4;      // Low sensitivity
        if (income > 50000) return 0.6;       // Medium sensitivity
        if (income > 25000) return 0.8;       // High sensitivity
        return 1.0;                           // Very high sensitivity
    }

    private double calculateDebtFactor(UserData userData) {
        if (userData.getExistingDebt() == null || userData.getIncome() == null || userData.getIncome() == 0) {
            return 0.5; // Default to medium sensitivity if debt/income is unknown
        }

        double debtToIncomeRatio = userData.getExistingDebt() / userData.getIncome();
        
        if (debtToIncomeRatio > 0.5) return 1.0;      // Very high sensitivity
        if (debtToIncomeRatio > 0.3) return 0.8;      // High sensitivity
        if (debtToIncomeRatio > 0.2) return 0.6;      // Medium sensitivity
        if (debtToIncomeRatio > 0.1) return 0.4;      // Low sensitivity
        return 0.2;                                   // Very low sensitivity
    }

    private double calculateAgeFactor(UserData userData) {
        if (userData.getAge() == null) {
            return 0.5; // Default to medium sensitivity if age is unknown
        }

        int age = userData.getAge();
        if (age < 25) return 0.8;      // High sensitivity
        if (age < 35) return 0.6;      // Medium-high sensitivity
        if (age < 45) return 0.5;      // Medium sensitivity
        if (age < 55) return 0.4;      // Medium-low sensitivity
        return 0.3;                    // Low sensitivity
    }

    private void validateValue(double value, String name) {
        if (value < 0 || value > 1) {
            throw new IllegalArgumentException(name + " must be between 0 and 1");
        }
    }
} 