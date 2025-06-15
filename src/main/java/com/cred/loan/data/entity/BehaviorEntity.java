package com.cred.loan.data.entity;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Entity class representing user behavior data in the database.
 */
public class BehaviorEntity {
    private String userId;
    private List<PageView> pageViews;
    private List<LoanCalculator> calculatorUsage;
    private List<OfferInteraction> offerInteractions;
    private LocalDateTime lastActive;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Getters and Setters
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<PageView> getPageViews() {
        return pageViews;
    }

    public void setPageViews(List<PageView> pageViews) {
        this.pageViews = pageViews;
    }

    public List<LoanCalculator> getCalculatorUsage() {
        return calculatorUsage;
    }

    public void setCalculatorUsage(List<LoanCalculator> calculatorUsage) {
        this.calculatorUsage = calculatorUsage;
    }

    public List<OfferInteraction> getOfferInteractions() {
        return offerInteractions;
    }

    public void setOfferInteractions(List<OfferInteraction> offerInteractions) {
        this.offerInteractions = offerInteractions;
    }

    public LocalDateTime getLastActive() {
        return lastActive;
    }

    public void setLastActive(LocalDateTime lastActive) {
        this.lastActive = lastActive;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
} 