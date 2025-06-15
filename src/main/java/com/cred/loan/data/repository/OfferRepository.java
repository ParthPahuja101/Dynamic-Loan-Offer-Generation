package com.cred.loan.data.repository;

import com.cred.loan.core.model.OfferData;
import java.util.List;
import java.util.Optional;

/**
 * Repository interface for offer data persistence operations.
 */
public interface OfferRepository {
    /**
     * Finds an offer by its ID.
     *
     * @param offerId the ID of the offer to find
     * @return an Optional containing the offer data if found, empty otherwise
     */
    Optional<OfferData> findById(String offerId);

    /**
     * Finds all offers for a specific user.
     *
     * @param userId the ID of the user whose offers to find
     * @return a list of offer data for the user
     */
    List<OfferData> findByUserId(String userId);

    /**
     * Saves offer data.
     *
     * @param offerData the offer data to save
     * @return the saved offer data
     */
    OfferData save(OfferData offerData);
} 