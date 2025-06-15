package com.cred.loan.core.model;

import java.util.Objects;

/**
 * Class representing a request for loan offer generation.
 * Contains all necessary information to generate personalized loan offers.
 */
public class LoanOfferRequest {
    private final String userId;
    private final Double requestedAmount;
    private final Integer preferredTenure;
    private final String purpose;
    private final String source;

    /**
     * Creates a new loan offer request.
     *
     * @param userId The user ID
     * @param requestedAmount The requested loan amount
     * @param preferredTenure The preferred loan tenure in months
     * @param purpose The purpose of the loan
     * @param source The source of the request
     */
    public LoanOfferRequest(String userId, Double requestedAmount, Integer preferredTenure, 
                          String purpose, String source) {
        this.userId = Objects.requireNonNull(userId, "User ID cannot be null");
        this.requestedAmount = Objects.requireNonNull(requestedAmount, "Requested amount cannot be null");
        this.preferredTenure = Objects.requireNonNull(preferredTenure, "Preferred tenure cannot be null");
        this.purpose = Objects.requireNonNull(purpose, "Purpose cannot be null");
        this.source = Objects.requireNonNull(source, "Source cannot be null");
    }

    /**
     * Gets the user ID.
     *
     * @return The user ID
     */
    public String getUserId() {
        return userId;
    }

    /**
     * Gets the requested loan amount.
     *
     * @return The requested amount
     */
    public Double getRequestedAmount() {
        return requestedAmount;
    }

    /**
     * Gets the preferred loan tenure.
     *
     * @return The preferred tenure
     */
    public Integer getPreferredTenure() {
        return preferredTenure;
    }

    /**
     * Gets the purpose of the loan.
     *
     * @return The loan purpose
     */
    public String getPurpose() {
        return purpose;
    }

    /**
     * Gets the source of the request.
     *
     * @return The request source
     */
    public String getSource() {
        return source;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LoanOfferRequest that = (LoanOfferRequest) o;
        return Objects.equals(userId, that.userId) &&
               Objects.equals(requestedAmount, that.requestedAmount) &&
               Objects.equals(preferredTenure, that.preferredTenure) &&
               Objects.equals(purpose, that.purpose) &&
               Objects.equals(source, that.source);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, requestedAmount, preferredTenure, purpose, source);
    }

    @Override
    public String toString() {
        return "LoanOfferRequest{" +
               "userId='" + userId + '\'' +
               ", requestedAmount=" + requestedAmount +
               ", preferredTenure=" + preferredTenure +
               ", purpose='" + purpose + '\'' +
               ", source='" + source + '\'' +
               '}';
    }
} 