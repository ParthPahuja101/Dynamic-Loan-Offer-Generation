package com.cred.loan.core.model.impl;

import com.cred.loan.core.model.RiskAssessmentResult;
import com.cred.loan.core.model.RiskLevel;

import java.util.Map;
import java.util.Set;

/**
 * Implementation of the RiskAssessmentResult interface.
 */
public class RiskAssessmentResultImpl implements RiskAssessmentResult {
    private final Double riskScore;
    private final Double roiRange;
    private final RiskLevel riskLevel;
    private final Set<String> riskFactors;
    private final Map<String, String> riskFactorExplanations;

    public RiskAssessmentResultImpl(
            Double riskScore,
            Double roiRange,
            RiskLevel riskLevel,
            Set<String> riskFactors,
            Map<String, String> riskFactorExplanations) {
        this.riskScore = riskScore;
        this.roiRange = roiRange;
        this.riskLevel = riskLevel;
        this.riskFactors = riskFactors;
        this.riskFactorExplanations = riskFactorExplanations;
    }

    @Override
    public Double getRiskScore() {
        return riskScore;
    }

    @Override
    public Double getROIRange() {
        return roiRange;
    }

    @Override
    public RiskLevel getRiskLevel() {
        return riskLevel;
    }

    @Override
    public Set<String> getRiskFactors() {
        return riskFactors;
    }

    @Override
    public Map<String, String> getRiskFactorExplanations() {
        return riskFactorExplanations;
    }
} 