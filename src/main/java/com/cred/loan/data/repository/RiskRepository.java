package com.cred.loan.data.repository;

import com.cred.loan.core.model.RiskData;
import java.util.Optional;

/**
 * Repository interface for risk data persistence operations.
 */
public interface RiskRepository {
    /**
     * Finds risk data for a specific user.
     *
     * @param userId the ID of the user whose risk data to find
     * @return an Optional containing the risk data if found, empty otherwise
     */
    Optional<RiskData> findByUserId(String userId);

    /**
     * Saves risk data.
     *
     * @param riskData the risk data to save
     * @return the saved risk data
     */
    RiskData save(RiskData riskData);
} 