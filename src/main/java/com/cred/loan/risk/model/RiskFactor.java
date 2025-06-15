package com.cred.loan.risk.model;

/**
 * Enum representing different risk factors that contribute to the overall risk assessment.
 * Each factor has a description and a weight that determines its importance in the risk calculation.
 */
public enum RiskFactor {
    CREDIT_SCORE("Credit Score", 0.3),
    FOIR("Fixed Obligation to Income Ratio", 0.25),
    UTILIZATION("Credit Utilization", 0.15),
    REPAYMENT_HISTORY("Repayment History", 0.2),
    INCOME_STABILITY("Income Stability", 0.1);

    private final String description;
    private final double weight;

    /**
     * Creates a new RiskFactor enum value.
     *
     * @param description Human-readable description of the risk factor
     * @param weight Weight of this factor in the overall risk calculation (0.0 to 1.0)
     */
    RiskFactor(String description, double weight) {
        this.description = description;
        this.weight = weight;
    }

    /**
     * Gets the human-readable description of this risk factor.
     *
     * @return The description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Gets the weight of this risk factor in the overall risk calculation.
     *
     * @return The weight (0.0 to 1.0)
     */
    public double getWeight() {
        return weight;
    }
} 