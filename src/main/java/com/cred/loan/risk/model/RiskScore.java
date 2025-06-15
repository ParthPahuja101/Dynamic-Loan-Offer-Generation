package com.cred.loan.risk.model;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Set;

/**
 * Represents a comprehensive risk score for a user, including overall score,
 * component scores for different risk factors, and identified risk factors.
 */
public class RiskScore {
    private final double overallScore;
    private final Map<RiskFactor, Double> componentScores;
    private final Set<RiskFactor> riskFactors;
    private final LocalDateTime calculatedAt;

    /**
     * Creates a new RiskScore instance.
     *
     * @param overallScore The overall risk score
     * @param componentScores Map of risk factors to their individual scores
     * @param riskFactors Set of identified risk factors
     * @param calculatedAt Timestamp when the score was calculated
     */
    public RiskScore(double overallScore, 
                    Map<RiskFactor, Double> componentScores,
                    Set<RiskFactor> riskFactors,
                    LocalDateTime calculatedAt) {
        this.overallScore = overallScore;
        this.componentScores = Map.copyOf(componentScores);
        this.riskFactors = Set.copyOf(riskFactors);
        this.calculatedAt = calculatedAt;
    }

    /**
     * Gets the overall risk score.
     *
     * @return The overall risk score
     */
    public double getOverallScore() {
        return overallScore;
    }

    /**
     * Gets the component score for a specific risk factor.
     *
     * @param factor The risk factor to get the score for
     * @return The component score for the specified risk factor
     */
    public double getComponentScore(RiskFactor factor) {
        return componentScores.getOrDefault(factor, 0.0);
    }

    /**
     * Gets all component scores.
     *
     * @return An unmodifiable map of risk factors to their scores
     */
    public Map<RiskFactor, Double> getComponentScores() {
        return componentScores;
    }

    /**
     * Gets all identified risk factors.
     *
     * @return An unmodifiable set of risk factors
     */
    public Set<RiskFactor> getRiskFactors() {
        return riskFactors;
    }

    /**
     * Gets the timestamp when the score was calculated.
     *
     * @return The calculation timestamp
     */
    public LocalDateTime getCalculatedAt() {
        return calculatedAt;
    }
} 