package com.cred.loan.core.model.impl;

import com.cred.loan.core.model.RiskData;

import java.time.LocalDateTime;
import java.util.Set;

/**
 * Implementation of the RiskData interface.
 */
public class RiskDataImpl implements RiskData {
    private final String userId;
    private final Double riskScore;
    private final String riskLevel;
    private final Set<String> riskFactors;
    private final LocalDateTime lastUpdated;

    public RiskDataImpl(
            String userId,
            Double riskScore,
            String riskLevel,
            Set<String> riskFactors,
            LocalDateTime lastUpdated) {
        this.userId = userId;
        this.riskScore = riskScore;
        this.riskLevel = riskLevel;
        this.riskFactors = riskFactors;
        this.lastUpdated = lastUpdated;
    }

    @Override
    public String getUserId() {
        return userId;
    }

    @Override
    public Double getRiskScore() {
        return riskScore;
    }

    @Override
    public String getRiskLevel() {
        return riskLevel;
    }

    @Override
    public Set<String> getRiskFactors() {
        return riskFactors;
    }

    @Override
    public LocalDateTime getLastUpdated() {
        return lastUpdated;
    }
} 