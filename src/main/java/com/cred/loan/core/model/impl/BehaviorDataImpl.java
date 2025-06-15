package com.cred.loan.core.model.impl;

import com.cred.loan.core.model.BehaviorData;
import com.cred.loan.core.model.PageView;
import com.cred.loan.core.model.LoanCalculator;
import com.cred.loan.core.model.OfferInteraction;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Implementation of the BehaviorData interface.
 */
public class BehaviorDataImpl implements BehaviorData {
    private final String userId;
    private final List<PageView> pageViews;
    private final List<LoanCalculator> calculatorUsage;
    private final List<OfferInteraction> offerInteractions;
    private final LocalDateTime lastActive;

    public BehaviorDataImpl(
            String userId,
            List<PageView> pageViews,
            List<LoanCalculator> calculatorUsage,
            List<OfferInteraction> offerInteractions,
            LocalDateTime lastActive) {
        this.userId = userId;
        this.pageViews = pageViews;
        this.calculatorUsage = calculatorUsage;
        this.offerInteractions = offerInteractions;
        this.lastActive = lastActive;
    }

    @Override
    public String getUserId() {
        return userId;
    }

    @Override
    public List<PageView> getPageViews() {
        return pageViews;
    }

    @Override
    public List<LoanCalculator> getCalculatorUsage() {
        return calculatorUsage;
    }

    @Override
    public List<OfferInteraction> getOfferInteractions() {
        return offerInteractions;
    }

    @Override
    public LocalDateTime getLastActive() {
        return lastActive;
    }
} 