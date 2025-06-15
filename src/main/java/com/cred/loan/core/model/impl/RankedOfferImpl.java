package com.cred.loan.core.model.impl;

import com.cred.loan.core.model.RankedOffer;
import com.cred.loan.core.model.OptimizedOffer;

/**
 * Implementation of the RankedOffer interface.
 */
public class RankedOfferImpl implements RankedOffer {
    private final OptimizedOffer offer;
    private final Double score;
    private final Integer rank;

    public RankedOfferImpl(OptimizedOffer offer, Double score, Integer rank) {
        this.offer = offer;
        this.score = score;
        this.rank = rank;
    }

    @Override
    public OptimizedOffer getOffer() {
        return offer;
    }

    @Override
    public Double getScore() {
        return score;
    }

    @Override
    public Integer getRank() {
        return rank;
    }
} 