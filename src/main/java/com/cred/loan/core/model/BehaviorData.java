package com.cred.loan.core.model;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Interface representing user behavior data used for offer optimization.
 * Contains information about user interactions and activities related to loan offers.
 */
public interface BehaviorData {
    /**
     * Gets the unique identifier of the user.
     *
     * @return The user ID
     */
    String getUserId();

    /**
     * Gets the list of page views made by the user.
     *
     * @return List of page views
     */
    List<PageView> getPageViews();

    /**
     * Gets the list of loan calculator usage instances.
     *
     * @return List of loan calculator usage
     */
    List<LoanCalculator> getCalculatorUsage();

    /**
     * Gets the list of offer interactions.
     *
     * @return List of offer interactions
     */
    List<OfferInteraction> getOfferInteractions();

    /**
     * Gets the timestamp of the user's last activity.
     *
     * @return The last active timestamp
     */
    LocalDateTime getLastActive();
} 