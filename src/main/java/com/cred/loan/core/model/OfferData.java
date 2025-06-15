package com.cred.loan.core.model;

import java.time.LocalDateTime;

/**
 * Interface representing offer data to be persisted.
 */
public interface OfferData {
    /**
     * Gets the offer ID.
     *
     * @return The offer ID
     */
    String getOfferId();

    /**
     * Gets the user ID.
     *
     * @return The user ID
     */
    String getUserId();

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
     * Gets the base ROI.
     *
     * @return The base ROI
     */
    Double getBaseROI();

    /**
     * Gets the adjusted ROI.
     *
     * @return The adjusted ROI
     */
    Double getAdjustedROI();

    /**
     * Gets the processing fee.
     *
     * @return The processing fee
     */
    Double getProcessingFee();

    /**
     * Gets the risk score.
     *
     * @return The risk score
     */
    Double getRiskScore();

    /**
     * Gets the conversion probability.
     *
     * @return The conversion probability
     */
    Double getConversionProbability();

    /**
     * Gets the creation timestamp.
     *
     * @return The creation timestamp
     */
    LocalDateTime getCreatedAt();
} 