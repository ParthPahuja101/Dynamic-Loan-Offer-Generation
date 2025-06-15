package com.cred.loan.core.model.impl;

import com.cred.loan.core.model.PriceSensitivity;
import java.time.LocalDateTime;

/**
 * Implementation of the PriceSensitivity interface.
 */
public class PriceSensitivityImpl implements PriceSensitivity {
    private final Double sensitivity;
    private final Double confidence;
    private final LocalDateTime calculatedAt;

    public PriceSensitivityImpl(Double sensitivity, Double confidence) {
        this.sensitivity = sensitivity;
        this.confidence = confidence;
        this.calculatedAt = LocalDateTime.now();
    }

    @Override
    public Double getSensitivity() {
        return sensitivity;
    }

    @Override
    public Double getConfidence() {
        return confidence;
    }

    @Override
    public LocalDateTime getCalculatedAt() {
        return calculatedAt;
    }
} 