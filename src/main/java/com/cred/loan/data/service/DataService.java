package com.cred.loan.data.service;

import com.cred.loan.core.model.UserData;
import com.cred.loan.core.model.BehaviorData;
import com.cred.loan.core.model.RiskData;
import com.cred.loan.core.model.OfferData;

import java.util.concurrent.CompletableFuture;

/**
 * Service interface for managing data access and persistence operations.
 * This service provides a unified interface for all data operations across the system.
 */
public interface DataService {
    /**
     * Retrieves user data for the specified user ID.
     *
     * @param userId The ID of the user
     * @return A CompletableFuture containing the user data
     * @throws UserNotFoundException if the user is not found
     */
    CompletableFuture<UserData> getUserData(String userId);

    /**
     * Retrieves behavior data for the specified user ID.
     *
     * @param userId The ID of the user
     * @return A CompletableFuture containing the behavior data
     */
    CompletableFuture<BehaviorData> getBehaviorData(String userId);

    /**
     * Retrieves risk data for the specified user ID.
     *
     * @param userId The ID of the user
     * @return A CompletableFuture containing the risk data
     * @throws RiskDataNotFoundException if the risk data is not found
     */
    CompletableFuture<RiskData> getRiskData(String userId);

    /**
     * Saves offer data to the database.
     *
     * @param offerData The offer data to save
     * @return A CompletableFuture that completes when the save operation is done
     */
    CompletableFuture<Void> saveOfferData(OfferData offerData);
} 