package com.cred.loan.core.model;

/**
 * Interface representing a ranked loan offer after applying scoring and ranking algorithms.
 * Contains the optimized offer along with its ranking information.
 */
public interface RankedOffer {
    /**
     * Gets the optimized offer being ranked.
     *
     * @return The optimized offer
     */
    OptimizedOffer getOffer();

    /**
     * Gets the calculated score for this offer.
     *
     * @return The offer score
     */
    Double getScore();

    /**
     * Gets the rank position of this offer among all offers.
     *
     * @return The rank position
     */
    Integer getRank();
} 