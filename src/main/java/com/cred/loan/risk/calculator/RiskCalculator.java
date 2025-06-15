package com.cred.loan.risk.calculator;

import com.cred.loan.core.model.UserData;
import com.cred.loan.risk.model.RiskFactor;
import com.cred.loan.risk.model.RiskScore;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.CompletableFuture;

/**
 * Calculates risk scores for users based on various risk factors.
 * This calculator implements a weighted scoring system where each risk factor
 * contributes to the overall risk score based on its assigned weight.
 */
public class RiskCalculator {
    private final Map<RiskFactor, Double> factorWeights;

    /**
     * Creates a new RiskCalculator instance.
     *
     * @param factorWeights Map of risk factors to their weights
     * @throws IllegalArgumentException if weights don't sum to 1.0
     */
    public RiskCalculator(Map<RiskFactor, Double> factorWeights) {
        validateWeights(factorWeights);
        this.factorWeights = new HashMap<>(factorWeights);
    }

    /**
     * Calculates the overall risk score for a user.
     *
     * @param userData The user data to evaluate
     * @return A CompletableFuture containing the calculated RiskScore
     */
    public CompletableFuture<RiskScore> calculateRiskScore(UserData userData) {
        return CompletableFuture.supplyAsync(() -> {
            Map<RiskFactor, Double> componentScores = new HashMap<>();
            Set<RiskFactor> riskFactors = new HashSet<>();

            // Calculate component scores for each risk factor
            for (RiskFactor factor : RiskFactor.values()) {
                double score = calculateComponentScore(factor, userData);
                componentScores.put(factor, score);
                
                // Add to risk factors if score exceeds threshold
                if (score > factor.getWeight()) {
                    riskFactors.add(factor);
                }
            }

            // Calculate overall score
            double overallScore = calculateOverallScore(componentScores);

            return new RiskScore(
                overallScore,
                componentScores,
                riskFactors,
                LocalDateTime.now()
            );
        });
    }

    /**
     * Calculates the component score for a specific risk factor.
     *
     * @param factor The risk factor to evaluate
     * @param userData The user data to evaluate
     * @return The component score (0.0 to 1.0)
     */
    private double calculateComponentScore(RiskFactor factor, UserData userData) {
        switch (factor) {
            case CREDIT_SCORE:
                return calculateCreditScoreImpact(userData.getCreditScore());
            case FOIR:
                return calculateFOIRImpact(userData.getIncome(), userData.getExistingDebt());
            case UTILIZATION:
                return calculateUtilizationImpact(userData.getExistingDebt(), userData.getIncome());
            case REPAYMENT_HISTORY:
                return calculateRepaymentHistoryImpact(userData);
            case INCOME_STABILITY:
                return calculateIncomeStabilityImpact(userData);
            default:
                return 0.0;
        }
    }

    /**
     * Calculates the overall risk score from component scores.
     *
     * @param componentScores Map of risk factors to their scores
     * @return The overall risk score (0.0 to 1.0)
     */
    private double calculateOverallScore(Map<RiskFactor, Double> componentScores) {
        return componentScores.entrySet().stream()
            .mapToDouble(entry -> entry.getValue() * factorWeights.get(entry.getKey()))
            .sum();
    }

    private double calculateCreditScoreImpact(Double creditScore) {
        if (creditScore == null) return 1.0;
        // Normalize credit score to 0-1 range (assuming 300-900 scale)
        return 1.0 - ((creditScore - 300) / 600);
    }

    private double calculateFOIRImpact(Double income, Double existingDebt) {
        if (income == null || income == 0 || existingDebt == null) return 1.0;
        double foir = existingDebt / income;
        // FOIR > 0.5 is considered high risk
        return Math.min(foir / 0.5, 1.0);
    }

    private double calculateUtilizationImpact(Double existingDebt, Double income) {
        if (existingDebt == null || income == null || income == 0) return 1.0;
        double utilization = existingDebt / income;
        // Utilization > 0.3 is considered high risk
        return Math.min(utilization / 0.3, 1.0);
    }

    private double calculateRepaymentHistoryImpact(UserData userData) {
        // Implementation would depend on available repayment history data
        // For now, return a default value
        return 0.5;
    }

    private double calculateIncomeStabilityImpact(UserData userData) {
        // Implementation would depend on employment tenure and status
        // For now, return a default value
        return 0.5;
    }

    private void validateWeights(Map<RiskFactor, Double> weights) {
        double sum = weights.values().stream()
            .mapToDouble(Double::doubleValue)
            .sum();
        
        if (Math.abs(sum - 1.0) > 0.0001) {
            throw new IllegalArgumentException("Risk factor weights must sum to 1.0");
        }
    }
} 