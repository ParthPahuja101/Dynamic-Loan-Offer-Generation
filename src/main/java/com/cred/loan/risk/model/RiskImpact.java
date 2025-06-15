package com.cred.loan.risk.model;

/**
 * Represents the impact of a risk factor on the overall risk assessment.
 * This class tracks both the magnitude and direction of the impact.
 */
public class RiskImpact {
    private final double impact;
    private final ImpactDirection direction;
    private final String description;

    /**
     * Creates a new RiskImpact instance.
     *
     * @param impact The magnitude of the impact (0.0 to 1.0)
     * @param direction The direction of the impact
     * @param description Human-readable description of the impact
     * @throws IllegalArgumentException if impact is not between 0 and 1
     */
    public RiskImpact(double impact, ImpactDirection direction, String description) {
        if (impact < 0 || impact > 1) {
            throw new IllegalArgumentException("Impact must be between 0 and 1");
        }
        this.impact = impact;
        this.direction = direction;
        this.description = description;
    }

    /**
     * Gets the magnitude of the impact.
     *
     * @return The impact value (0.0 to 1.0)
     */
    public double getImpact() {
        return impact;
    }

    /**
     * Gets the direction of the impact.
     *
     * @return The impact direction
     */
    public ImpactDirection getDirection() {
        return direction;
    }

    /**
     * Gets the human-readable description of the impact.
     *
     * @return The description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Calculates the effective impact by considering both magnitude and direction.
     *
     * @return The effective impact value
     */
    public double getEffectiveImpact() {
        return direction == ImpactDirection.INCREASE ? impact : -impact;
    }

    /**
     * Enum representing the direction of a risk impact.
     */
    public enum ImpactDirection {
        INCREASE("Increases risk"),
        DECREASE("Decreases risk");

        private final String description;

        ImpactDirection(String description) {
            this.description = description;
        }

        public String getDescription() {
            return description;
        }
    }
} 