package com.cred.loan.optimization.matrix;

import com.cred.loan.optimization.model.TermAdjustment;
import com.cred.loan.optimization.model.TermConstraints;
import com.cred.loan.optimization.model.TermType;
import com.cred.loan.optimization.model.UserProfile;

import java.util.List;

/**
 * Matrix for calculating loan tenure adjustments based on user profiles
 * and business rules.
 */
public class TenureMatrix implements TermAdjustmentMatrix {
    private final List<Integer> availableTenures;
    private final int baseTenure;
    private final int minTenure;
    private final int maxTenure;
    private final double adjustmentRange;
    private final TermConstraints constraints;

    /**
     * Creates a new TenureMatrix instance.
     *
     * @param availableTenures List of available tenure options in months
     * @param baseTenure Base tenure value
     * @param minTenure Minimum allowed tenure
     * @param maxTenure Maximum allowed tenure
     * @param adjustmentRange Maximum adjustment range
     * @throws IllegalArgumentException if tenure values are invalid
     */
    public TenureMatrix(
            List<Integer> availableTenures,
            int baseTenure,
            int minTenure,
            int maxTenure,
            double adjustmentRange) {
        validateTenureValues(availableTenures, baseTenure, minTenure, maxTenure, adjustmentRange);
        this.availableTenures = availableTenures;
        this.baseTenure = baseTenure;
        this.minTenure = minTenure;
        this.maxTenure = maxTenure;
        this.adjustmentRange = adjustmentRange;
        this.constraints = new TermConstraints(minTenure, maxTenure, baseTenure, adjustmentRange);
    }

    @Override
    public TermAdjustment getAdjustment(TermType termType, UserProfile profile) {
        if (termType != TermType.TENURE) {
            throw new IllegalArgumentException(
                "TenureMatrix can only adjust tenure terms"
            );
        }

        double sensitivity = profile.getPriceSensitivity().getSensitivity();
        double confidence = profile.getPriceSensitivity().getConfidence();
        double longTermValue = profile.getLongTermValue();

        // Calculate adjustment based on user profile
        int adjustment = calculateAdjustment(sensitivity, confidence, longTermValue);
        int adjustedValue = findNearestAvailableTenure(baseTenure + adjustment);
        double impact = calculateImpact(adjustment);

        return new TermAdjustment(termType, baseTenure, adjustedValue, impact);
    }

    @Override
    public boolean validateAdjustment(TermAdjustment adjustment) {
        if (adjustment.getTermType() != TermType.TENURE) {
            return false;
        }

        int adjustedValue = (int) adjustment.getAdjustedValue();
        return constraints.isInRange(adjustedValue) &&
               constraints.isAdjustmentValid(adjustedValue) &&
               availableTenures.contains(adjustedValue);
    }

    @Override
    public TermConstraints getConstraints() {
        return constraints;
    }

    /**
     * Calculates the tenure adjustment based on user profile factors.
     *
     * @param sensitivity User's price sensitivity
     * @param confidence Confidence in the sensitivity calculation
     * @param longTermValue User's long-term value
     * @return Calculated adjustment in months
     */
    private int calculateAdjustment(
            double sensitivity,
            double confidence,
            double longTermValue) {
        // Higher sensitivity = shorter tenure
        double sensitivityAdjustment = -sensitivity * adjustmentRange;

        // Higher confidence = larger adjustment
        double confidenceFactor = confidence;

        // Higher long-term value = longer tenure
        double valueFactor = Math.min(1.0, longTermValue / 1000000.0);

        return (int) (sensitivityAdjustment * confidenceFactor * (1 - valueFactor));
    }

    /**
     * Finds the nearest available tenure option.
     *
     * @param targetTenure Target tenure value
     * @return Nearest available tenure
     */
    private int findNearestAvailableTenure(int targetTenure) {
        return availableTenures.stream()
            .min((a, b) -> Integer.compare(
                Math.abs(a - targetTenure),
                Math.abs(b - targetTenure)
            ))
            .orElse(baseTenure);
    }

    /**
     * Calculates the impact of a tenure adjustment.
     *
     * @param adjustment Tenure adjustment
     * @return Impact value
     */
    private double calculateImpact(int adjustment) {
        // Impact is proportional to the absolute adjustment size
        return Math.abs(adjustment) / adjustmentRange;
    }

    /**
     * Validates tenure values.
     *
     * @param availableTenures List of available tenure options
     * @param baseTenure Base tenure value
     * @param minTenure Minimum allowed tenure
     * @param maxTenure Maximum allowed tenure
     * @param adjustmentRange Maximum adjustment range
     * @throws IllegalArgumentException if values are invalid
     */
    private void validateTenureValues(
            List<Integer> availableTenures,
            int baseTenure,
            int minTenure,
            int maxTenure,
            double adjustmentRange) {
        if (availableTenures == null || availableTenures.isEmpty()) {
            throw new IllegalArgumentException(
                "Available tenures list cannot be null or empty"
            );
        }

        if (minTenure > maxTenure) {
            throw new IllegalArgumentException(
                "Minimum tenure cannot be greater than maximum tenure"
            );
        }

        if (baseTenure < minTenure || baseTenure > maxTenure) {
            throw new IllegalArgumentException(
                "Base tenure must be between minimum and maximum tenure"
            );
        }

        if (adjustmentRange < 0) {
            throw new IllegalArgumentException(
                "Adjustment range cannot be negative"
            );
        }

        if (!availableTenures.contains(baseTenure)) {
            throw new IllegalArgumentException(
                "Base tenure must be in the available tenures list"
            );
        }

        if (availableTenures.stream().anyMatch(t -> t < minTenure || t > maxTenure)) {
            throw new IllegalArgumentException(
                "All available tenures must be between minimum and maximum tenure"
            );
        }
    }
} 