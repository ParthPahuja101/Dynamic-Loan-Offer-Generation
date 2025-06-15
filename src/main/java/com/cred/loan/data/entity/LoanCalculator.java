package com.cred.loan.data.entity;

import java.time.LocalDateTime;

/**
 * Entity class representing a loan calculator usage event.
 */
public class LoanCalculator {
    private Double amount;
    private Integer tenure;
    private Double roi;
    private LocalDateTime timestamp;

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Integer getTenure() {
        return tenure;
    }

    public void setTenure(Integer tenure) {
        this.tenure = tenure;
    }

    public Double getRoi() {
        return roi;
    }

    public void setRoi(Double roi) {
        this.roi = roi;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
} 