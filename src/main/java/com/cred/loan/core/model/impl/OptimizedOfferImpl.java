package com.cred.loan.core.model.impl;

import com.cred.loan.core.model.BaseOffer;
import com.cred.loan.core.model.OptimizedOffer;

/**
 * Implementation of OptimizedOffer interface.
 */
public class OptimizedOfferImpl implements OptimizedOffer {
    private final BaseOffer baseOffer;
    private final Double adjustedROI;
    private final Double riskImpact;
    private final Double conversionProbability;

    public OptimizedOfferImpl(
            BaseOffer baseOffer,
            Double adjustedROI,
            Double riskImpact,
            Double conversionProbability) {
        this.baseOffer = baseOffer;
        this.adjustedROI = adjustedROI;
        this.riskImpact = riskImpact;
        this.conversionProbability = conversionProbability;
    }

    @Override
    public BaseOffer getBaseOffer() {
        return baseOffer;
    }

    @Override
    public Double getAdjustedROI() {
        return adjustedROI;
    }

    @Override
    public Double getRiskImpact() {
        return riskImpact;
    }

    @Override
    public Double getConversionProbability() {
        return conversionProbability;
    }
} 