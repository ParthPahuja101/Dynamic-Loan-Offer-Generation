package com.cred.loan.core.model.impl;

import com.cred.loan.core.model.BehaviorAnalysisResult;
import com.cred.loan.core.model.PriceSensitivity;

import java.time.LocalDateTime;

/**
 * Implementation of the BehaviorAnalysisResult interface.
 */
public class BehaviorAnalysisResultImpl implements BehaviorAnalysisResult {
    private final PriceSensitivity priceSensitivity;
    private final Double conversionProbability;
    private final Double longTermValue;
    private final LocalDateTime analyzedAt;

    public BehaviorAnalysisResultImpl(
            PriceSensitivity priceSensitivity,
            Double conversionProbability,
            Double longTermValue,
            LocalDateTime analyzedAt) {
        this.priceSensitivity = priceSensitivity;
        this.conversionProbability = conversionProbability;
        this.longTermValue = longTermValue;
        this.analyzedAt = analyzedAt;
    }

    @Override
    public PriceSensitivity getPriceSensitivity() {
        return priceSensitivity;
    }

    @Override
    public Double getConversionProbability() {
        return conversionProbability;
    }

    @Override
    public Double getLongTermValue() {
        return longTermValue;
    }

    @Override
    public LocalDateTime getAnalyzedAt() {
        return analyzedAt;
    }
} 