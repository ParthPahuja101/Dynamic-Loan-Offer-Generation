package com.cred.loan.behavior.analyzer;

import com.cred.loan.core.model.UserData;
import com.cred.loan.behavior.model.BehaviorAnalysisResult;
import com.cred.loan.behavior.model.PriceSensitivity;

import java.time.LocalDateTime;
import java.util.concurrent.CompletableFuture;

/**
 * Main behavior analysis engine that coordinates the analysis of user behavior
 * patterns and generates comprehensive behavior insights.
 */
public class BehaviorAnalyzer {
    private final PriceSensitivityCalculator priceSensitivityCalculator;
    private final ConversionProbabilityCalculator conversionCalculator;

    /**
     * Creates a new BehaviorAnalyzer instance.
     *
     * @param priceSensitivityCalculator Calculator for price sensitivity
     * @param conversionCalculator Calculator for conversion probability
     */
    public BehaviorAnalyzer(
            PriceSensitivityCalculator priceSensitivityCalculator,
            ConversionProbabilityCalculator conversionCalculator) {
        this.priceSensitivityCalculator = priceSensitivityCalculator;
        this.conversionCalculator = conversionCalculator;
    }

    /**
     * Analyzes user behavior and generates comprehensive behavior insights.
     *
     * @param userData The user data to analyze
     * @return A CompletableFuture containing the behavior analysis result
     */
    public CompletableFuture<BehaviorAnalysisResult> analyzeBehavior(UserData userData) {
        return CompletableFuture.supplyAsync(() -> {
            // Calculate price sensitivity
            PriceSensitivity priceSensitivity = priceSensitivityCalculator
                .calculateSensitivity(userData)
                .join();

            // Calculate conversion probability
            double conversionProbability = conversionCalculator
                .calculateProbability(userData)
                .join();

            // Calculate long-term value
            double longTermValue = calculateLongTermValue(userData);

            return new BehaviorAnalysisResult(
                priceSensitivity,
                conversionProbability,
                longTermValue,
                LocalDateTime.now()
            );
        });
    }

    /**
     * Calculates the long-term value of a user based on various factors.
     *
     * @param userData The user data to evaluate
     * @return The long-term value score (0.0 to 1.0)
     */
    private double calculateLongTermValue(UserData userData) {
        double incomeStabilityScore = calculateIncomeStabilityScore(userData);
        double employmentScore = calculateEmploymentScore(userData);
        double locationScore = calculateLocationScore(userData);

        // Weight the factors based on their importance
        return (incomeStabilityScore * 0.4) +
               (employmentScore * 0.4) +
               (locationScore * 0.2);
    }

    /**
     * Calculates the income stability score based on employment tenure and status.
     *
     * @param userData The user data to evaluate
     * @return The income stability score (0.0 to 1.0)
     */
    private double calculateIncomeStabilityScore(UserData userData) {
        if (userData.getEmploymentStatus() == null || userData.getEmploymentTenure() == null) {
            return 0.5; // Default to medium stability if data is missing
        }

        double statusScore = switch (userData.getEmploymentStatus().toLowerCase()) {
            case "permanent" -> 1.0;
            case "contract" -> 0.7;
            case "self-employed" -> 0.6;
            default -> 0.5;
        };

        // Consider employment tenure (in months)
        double tenureScore = Math.min(userData.getEmploymentTenure() / 24.0, 1.0);

        return (statusScore * 0.6) + (tenureScore * 0.4);
    }

    /**
     * Calculates the employment score based on employment status and tenure.
     *
     * @param userData The user data to evaluate
     * @return The employment score (0.0 to 1.0)
     */
    private double calculateEmploymentScore(UserData userData) {
        if (userData.getEmploymentStatus() == null) {
            return 0.5; // Default to medium score if data is missing
        }

        return switch (userData.getEmploymentStatus().toLowerCase()) {
            case "permanent" -> 1.0;
            case "contract" -> 0.8;
            case "self-employed" -> 0.7;
            default -> 0.5;
        };
    }

    /**
     * Calculates the location score based on the user's city.
     *
     * @param userData The user data to evaluate
     * @return The location score (0.0 to 1.0)
     */
    private double calculateLocationScore(UserData userData) {
        if (userData.getCity() == null) {
            return 0.5; // Default to medium score if data is missing
        }

        // This would typically involve a more sophisticated city-based scoring system
        // For now, we'll use a simple tier-based approach
        return switch (userData.getCity().toLowerCase()) {
            case "mumbai", "delhi", "bangalore" -> 1.0;
            case "hyderabad", "chennai", "kolkata" -> 0.9;
            case "pune", "ahmedabad" -> 0.8;
            default -> 0.7;
        };
    }
} 