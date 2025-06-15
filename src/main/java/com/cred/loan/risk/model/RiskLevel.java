package com.cred.loan.risk.model;

/**
 * Enum representing different risk levels for loan applicants.
 * Each level has a score range and description.
 */
public enum RiskLevel {
    LOW(0.0, 0.3, "Low risk - Excellent credit profile"),
    MEDIUM(0.3, 0.7, "Medium risk - Standard credit profile"),
    HIGH(0.7, 1.0, "High risk - Poor credit profile");

    private final double minScore;
    private final double maxScore;
    private final String description;

    /**
     * Creates a new RiskLevel enum value.
     *
     * @param minScore Minimum score for this risk level (inclusive)
     * @param maxScore Maximum score for this risk level (exclusive)
     * @param description Human-readable description of the risk level
     */
    RiskLevel(double minScore, double maxScore, String description) {
        this.minScore = minScore;
        this.maxScore = maxScore;
        this.description = description;
    }

    /**
     * Determines the risk level from a given score.
     *
     * @param score The risk score to evaluate
     * @return The corresponding RiskLevel
     * @throws IllegalArgumentException if score is not between 0 and 1
     */
    public static RiskLevel fromScore(Double score) {
        if (score == null || score < 0 || score > 1) {
            throw new IllegalArgumentException("Score must be between 0 and 1");
        }

        for (RiskLevel level : values()) {
            if (score >= level.minScore && score < level.maxScore) {
                return level;
            }
        }
        return HIGH; // Default to HIGH if score is exactly 1.0
    }

    /**
     * Gets the minimum score for this risk level.
     *
     * @return The minimum score
     */
    public double getMinScore() {
        return minScore;
    }

    /**
     * Gets the maximum score for this risk level.
     *
     * @return The maximum score
     */
    public double getMaxScore() {
        return maxScore;
    }

    /**
     * Gets the human-readable description of this risk level.
     *
     * @return The description
     */
    public String getDescription() {
        return description;
    }
} 