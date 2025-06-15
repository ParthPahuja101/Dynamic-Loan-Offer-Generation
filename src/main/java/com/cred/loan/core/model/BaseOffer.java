package com.cred.loan.core.model;

/**
 * Interface representing a base loan offer before optimization.
 * Contains the fundamental loan terms and conditions.
 */
public interface BaseOffer {
    /**
     * Gets the loan amount.
     *
     * @return The loan amount
     */
    Double getAmount();

    /**
     * Gets the loan tenure in months.
     *
     * @return The loan tenure
     */
    Integer getTenure();

    /**
     * Gets the rate of interest (ROI) for the loan.
     *
     * @return The ROI
     */
    Double getROI();

    /**
     * Gets the processing fee for the loan.
     *
     * @return The processing fee
     */
    Double getProcessingFee();
} 