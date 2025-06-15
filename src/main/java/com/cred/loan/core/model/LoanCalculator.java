package com.cred.loan.core.model;

import java.time.LocalDateTime;

/**
 * Represents a loan calculator usage event.
 */
public class LoanCalculator {
    private final Double amount;
    private final Integer tenure;
    private final Double roi;
    private final LocalDateTime timestamp;

    public LoanCalculator(Double amount, Integer tenure, Double roi, LocalDateTime timestamp) {
        this.amount = amount;
        this.tenure = tenure;
        this.roi = roi;
        this.timestamp = timestamp;
    }

    public Double getAmount() {
        return amount;
    }

    public Integer getTenure() {
        return tenure;
    }

    public Double getRoi() {
        return roi;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
} 