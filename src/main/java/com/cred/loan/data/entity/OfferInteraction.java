package com.cred.loan.data.entity;

import java.time.LocalDateTime;

/**
 * Entity class representing an offer interaction event.
 */
public class OfferInteraction {
    private String offerId;
    private String interactionType;
    private LocalDateTime timestamp;

    public String getOfferId() {
        return offerId;
    }

    public void setOfferId(String offerId) {
        this.offerId = offerId;
    }

    public String getInteractionType() {
        return interactionType;
    }

    public void setInteractionType(String interactionType) {
        this.interactionType = interactionType;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
} 