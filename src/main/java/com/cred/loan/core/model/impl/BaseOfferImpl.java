package com.cred.loan.core.model.impl;

import com.cred.loan.core.model.BaseOffer;

/**
 * Implementation of the BaseOffer interface.
 */
public class BaseOfferImpl implements BaseOffer {
    private final Double amount;
    private final Integer tenure;
    private final Double roi;
    private final Double processingFee;

    public BaseOfferImpl(Double amount, Integer tenure, Double roi, Double processingFee) {
        this.amount = amount;
        this.tenure = tenure;
        this.roi = roi;
        this.processingFee = processingFee;
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
    public Double getROI() {
        return roi;
    }

    @Override
    public Double getProcessingFee() {
        return processingFee;
    }
} 