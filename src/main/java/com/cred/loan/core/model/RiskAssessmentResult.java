package com.cred.loan.core.model;

import java.util.Map;
import java.util.Set;

/**
 * Interface representing the result of a risk assessment.
 * Contains risk scores, ROI ranges, and other risk-related information.
 */
public interface RiskAssessmentResult {
    /**
     * Gets the calculated risk score (0.0 to 1.0).
     *
     * @return The risk score
     */
    Double getRiskScore();

    /**
     * Gets the calculated ROI range.
     *
     * @return The ROI range
     */
    Double getROIRange();

    /**
     * Gets the risk level category.
     *
     * @return The risk level
     */
    RiskLevel getRiskLevel();

    /**
     * Gets the set of identified risk factors.
     *
     * @return The risk factors
     */
    Set<String> getRiskFactors();

    /**
     * Gets the explanations for each risk factor.
     *
     * @return Map of risk factor to explanation
     */
    Map<String, String> getRiskFactorExplanations();
} 