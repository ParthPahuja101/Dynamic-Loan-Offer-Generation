package com.cred.loan.core.model.impl;

import com.cred.loan.core.model.OfferData;

import java.time.LocalDateTime;

/**
 * Implementation of the OfferData interface.
 */
public class OfferDataImpl implements OfferData {
    private final String offerId;
    private final String userId;
    private final Double amount;
    private final Integer tenure;
    private final Double baseROI;
    private final Double adjustedROI;
    private final Double processingFee;
    private final Double riskScore;
    private final Double conversionProbability;
    private final LocalDateTime createdAt;

    public OfferDataImpl(
            String offerId,
            String userId,
            Double amount,
            Integer tenure,
            Double baseROI,
            Double adjustedROI,
            Double processingFee,
            Double riskScore,
            Double conversionProbability,
            LocalDateTime createdAt) {
        this.offerId = offerId;
        this.userId = userId;
        this.amount = amount;
        this.tenure = tenure;
        this.baseROI = baseROI;
        this.adjustedROI = adjustedROI;
        this.processingFee = processingFee;
        this.riskScore = riskScore;
        this.conversionProbability = conversionProbability;
        this.createdAt = createdAt;
    }

    @Override
    public String getOfferId() {
        return offerId;
    }

    @Override
    public String getUserId() {
        return userId;
    }

    @Override
    public Double getAmount() {
        return amount;
    }

    @Override
    public Integer getTenure() {
        return tenure;
    }

    @Override
    public Double getBaseROI() {
        return baseROI;
    }

    @Override
    public Double getAdjustedROI() {
        return adjustedROI;
    }

    @Override
    public Double getProcessingFee() {
        return processingFee;
    }

    @Override
    public Double getRiskScore() {
        return riskScore;
    }

    @Override
    public Double getConversionProbability() {
        return conversionProbability;
    }

    @Override
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
} 