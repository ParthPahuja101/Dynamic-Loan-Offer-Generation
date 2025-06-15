package com.cred.loan.core.model;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Class representing the response containing ranked loan offers.
 * Contains a list of ranked offers and metadata about the offer generation process.
 */
public class LoanOfferResponse {
    private final List<RankedOffer> offers;
    private final String requestId;
    private final long generationTimeMillis;

    /**
     * Creates a new loan offer response.
     *
     * @param offers The list of ranked offers
     * @param requestId The unique identifier for this request
     * @param generationTimeMillis The time taken to generate offers in milliseconds
     */
    public LoanOfferResponse(List<RankedOffer> offers, String requestId, long generationTimeMillis) {
        this.offers = Collections.unmodifiableList(Objects.requireNonNull(offers, "Offers cannot be null"));
        this.requestId = Objects.requireNonNull(requestId, "Request ID cannot be null");
        this.generationTimeMillis = generationTimeMillis;
    }

    /**
     * Gets the list of ranked offers.
     *
     * @return Unmodifiable list of ranked offers
     */
    public List<RankedOffer> getOffers() {
        return offers;
    }

    /**
     * Gets the request identifier.
     *
     * @return The request ID
     */
    public String getRequestId() {
        return requestId;
    }

    /**
     * Gets the time taken to generate offers.
     *
     * @return The generation time in milliseconds
     */
    public long getGenerationTimeMillis() {
        return generationTimeMillis;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LoanOfferResponse that = (LoanOfferResponse) o;
        return generationTimeMillis == that.generationTimeMillis &&
               Objects.equals(offers, that.offers) &&
               Objects.equals(requestId, that.requestId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(offers, requestId, generationTimeMillis);
    }

    @Override
    public String toString() {
        return "LoanOfferResponse{" +
               "offers=" + offers +
               ", requestId='" + requestId + '\'' +
               ", generationTimeMillis=" + generationTimeMillis +
               '}';
    }
} 