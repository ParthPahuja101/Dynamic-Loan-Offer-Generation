package com.cred.loan.data.repository;

import com.cred.loan.core.model.BehaviorData;
import java.util.Optional;

/**
 * Repository interface for behavior data persistence operations.
 */
public interface BehaviorRepository {
    /**
     * Finds behavior data for a specific user.
     *
     * @param userId the ID of the user whose behavior data to find
     * @return an Optional containing the behavior data if found, empty otherwise
     */
    Optional<BehaviorData> findByUserId(String userId);

    /**
     * Saves behavior data.
     *
     * @param behaviorData the behavior data to save
     * @return the saved behavior data
     */
    BehaviorData save(BehaviorData behaviorData);
} 