package com.cred.loan.behavior.analyzer;

import com.cred.loan.core.model.UserData;
import java.util.concurrent.CompletableFuture;

/**
 * Calculates the probability of a user converting on a loan offer based on
 * various factors including user profile, behavior patterns, and market conditions.
 */
public class ConversionProbabilityCalculator {
    private final double baseProbability;
    private final double maxProbability;

    /**
     * Creates a new ConversionProbabilityCalculator instance.
     *
     * @param baseProbability Base conversion probability (0.0 to 1.0)
     * @param maxProbability Maximum possible probability (0.0 to 1.0)
     * @throws IllegalArgumentException if probabilities are not between 0 and 1
     */
    public ConversionProbabilityCalculator(double baseProbability, double maxProbability) {
        validateProbability(baseProbability, "base probability");
        validateProbability(maxProbability, "max probability");
        
        if (baseProbability > maxProbability) {
            throw new IllegalArgumentException("Base probability cannot be greater than max probability");
        }

        this.baseProbability = baseProbability;
        this.maxProbability = maxProbability;
    }

    /**
     * Calculates the conversion probability for a user.
     *
     * @param userData The user data to evaluate
     * @return A CompletableFuture containing the calculated probability
     */
    public CompletableFuture<Double> calculateProbability(UserData userData) {
        return CompletableFuture.supplyAsync(() -> {
            double profileScore = calculateProfileScore(userData);
            double behaviorScore = calculateBehaviorScore(userData);
            double marketScore = calculateMarketScore(userData);

            // Weight the factors
            double weightedScore = (profileScore * 0.4) +
                                 (behaviorScore * 0.4) +
                                 (marketScore * 0.2);

            // Apply base probability and adjustments
            return Math.min(maxProbability,
                baseProbability + (weightedScore * (maxProbability - baseProbability)));
        });
    }

    /**
     * Calculates the profile-based conversion score.
     *
     * @param userData The user data to evaluate
     * @return The profile score (0.0 to 1.0)
     */
    private double calculateProfileScore(UserData userData) {
        double incomeScore = calculateIncomeScore(userData);
        double employmentScore = calculateEmploymentScore(userData);
        double creditScore = calculateCreditScore(userData);

        return (incomeScore * 0.4) +
               (employmentScore * 0.3) +
               (creditScore * 0.3);
    }

    /**
     * Calculates the behavior-based conversion score.
     *
     * @param userData The user data to evaluate
     * @return The behavior score (0.0 to 1.0)
     */
    private double calculateBehaviorScore(UserData userData) {
        // This would typically involve analyzing user behavior data
        // For now, we'll use a simplified approach based on available data
        double deviceScore = calculateDeviceScore(userData);
        double locationScore = calculateLocationScore(userData);

        return (deviceScore * 0.6) + (locationScore * 0.4);
    }

    /**
     * Calculates the market-based conversion score.
     *
     * @param userData The user data to evaluate
     * @return The market score (0.0 to 1.0)
     */
    private double calculateMarketScore(UserData userData) {
        // This would typically involve market analysis
        // For now, we'll use a simplified approach based on location
        return calculateLocationScore(userData);
    }

    private double calculateIncomeScore(UserData userData) {
        if (userData.getIncome() == null) {
            return 0.5; // Default to medium score if income is unknown
        }

        double income = userData.getIncome();
        if (income > 200000) return 1.0;      // Very high probability
        if (income > 100000) return 0.9;      // High probability
        if (income > 50000) return 0.8;       // Medium-high probability
        if (income > 25000) return 0.7;       // Medium probability
        return 0.6;                           // Medium-low probability
    }

    private double calculateEmploymentScore(UserData userData) {
        if (userData.getEmploymentStatus() == null) {
            return 0.5; // Default to medium score if employment status is unknown
        }

        return switch (userData.getEmploymentStatus().toLowerCase()) {
            case "permanent" -> 1.0;
            case "contract" -> 0.8;
            case "self-employed" -> 0.7;
            default -> 0.5;
        };
    }

    private double calculateCreditScore(UserData userData) {
        if (userData.getCreditScore() == null) {
            return 0.5; // Default to medium score if credit score is unknown
        }

        double score = userData.getCreditScore();
        if (score > 750) return 1.0;      // Very high probability
        if (score > 700) return 0.9;      // High probability
        if (score > 650) return 0.8;      // Medium-high probability
        if (score > 600) return 0.7;      // Medium probability
        return 0.6;                       // Medium-low probability
    }

    private double calculateDeviceScore(UserData userData) {
        if (userData.getDeviceType() == null) {
            return 0.5; // Default to medium score if device type is unknown
        }

        return switch (userData.getDeviceType().toLowerCase()) {
            case "mobile" -> 0.9;  // Higher conversion on mobile
            case "tablet" -> 0.8;  // Good conversion on tablet
            case "desktop" -> 0.7; // Lower conversion on desktop
            default -> 0.5;
        };
    }

    private double calculateLocationScore(UserData userData) {
        if (userData.getCity() == null) {
            return 0.5; // Default to medium score if city is unknown
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

    private void validateProbability(double probability, String name) {
        if (probability < 0 || probability > 1) {
            throw new IllegalArgumentException(name + " must be between 0 and 1");
        }
    }
} 