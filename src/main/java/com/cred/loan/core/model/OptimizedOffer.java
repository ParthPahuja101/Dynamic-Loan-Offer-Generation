package com.cred.loan.core.model;

/**
 * Interface representing an optimized loan offer after applying business rules and user behavior analysis.
 * Extends the base offer with additional optimization-related information.
 */
public interface OptimizedOffer {
    /**
     * Gets the base offer before optimization.
     *
     * @return The base offer
     */
    BaseOffer getBaseOffer();

    /**
     * Gets the adjusted rate of interest after optimization.
     *
     * @return The adjusted ROI
     */
    Double getAdjustedROI();

    /**
     * Gets the risk impact score of the optimization.
     *
     * @return The risk impact score
     */
    Double getRiskImpact();

    /**
     * Gets the probability of offer acceptance based on user behavior analysis.
     *
     * @return The conversion probability
     */
    Double getConversionProbability();
} 