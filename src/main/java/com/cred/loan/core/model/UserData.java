package com.cred.loan.core.model;

/**
 * Interface representing user data used for loan offer generation.
 * Contains essential user information required for risk assessment and offer generation.
 */
public interface UserData {
    /**
     * Gets the unique identifier of the user.
     *
     * @return The user ID
     */
    String getUserId();

    /**
     * Gets the user's credit score.
     *
     * @return The credit score
     */
    Double getCreditScore();

    /**
     * Gets the user's monthly income.
     *
     * @return The monthly income
     */
    Double getIncome();

    /**
     * Gets the user's existing debt amount.
     *
     * @return The existing debt amount
     */
    Double getExistingDebt();

    /**
     * Gets the user's age.
     *
     * @return The age
     */
    Integer getAge();

    /**
     * Gets the user's employment status.
     *
     * @return The employment status
     */
    String getEmploymentStatus();

    /**
     * Gets the user's employment tenure in months.
     *
     * @return The employment tenure
     */
    Integer getEmploymentTenure();

    /**
     * Gets the user's city of residence.
     *
     * @return The city
     */
    String getCity();

    /**
     * Gets the user's device type.
     *
     * @return The device type
     */
    String getDeviceType();
} 