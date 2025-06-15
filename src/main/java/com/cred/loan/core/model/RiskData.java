package com.cred.loan.core.model;

import java.time.LocalDateTime;
import java.util.Set;

/**
 * Interface representing risk-related data for a user.
 */
public interface RiskData {
    /**
     * Gets the user ID.
     *
     * @return The user ID
     */
    String getUserId();

    /**
     * Gets the risk score.
     *
     * @return The risk score
     */
    Double getRiskScore();

    /**
     * Gets the risk level.
     *
     * @return The risk level
     */
    String getRiskLevel();

    /**
     * Gets the risk factors.
     *
     * @return The risk factors
     */
    Set<String> getRiskFactors();

    /**
     * Gets the last updated timestamp.
     *
     * @return The last updated timestamp
     */
    LocalDateTime getLastUpdated();
} 