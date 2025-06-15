package com.cred.loan.core.model;

import java.time.LocalDateTime;

/**
 * Represents an interaction with a loan offer.
 */
public class OfferInteraction {
    private final String offerId;
    private final String interactionType;
    private final LocalDateTime timestamp;

    public OfferInteraction(String offerId, String interactionType, LocalDateTime timestamp) {
        this.offerId = offerId;
        this.interactionType = interactionType;
        this.timestamp = timestamp;
    }

    public String getOfferId() {
        return offerId;
    }

    public String getInteractionType() {
        return interactionType;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
} 