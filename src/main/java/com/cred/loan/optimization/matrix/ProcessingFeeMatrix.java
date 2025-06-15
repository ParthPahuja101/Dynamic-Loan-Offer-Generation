package com.cred.loan.optimization.matrix;

import com.cred.loan.optimization.model.TermAdjustment;
import com.cred.loan.optimization.model.TermConstraints;
import com.cred.loan.optimization.model.TermType;
import com.cred.loan.optimization.model.UserProfile;

/**
 * Matrix for calculating processing fee adjustments based on user profiles
 * and business rules.
 */
public class ProcessingFeeMatrix implements TermAdjustmentMatrix {
    private final double baseFee;
    private final double minFee;
    private final double maxFee;
    private final double adjustmentRange;
    private final TermConstraints constraints;

    /**
     * Creates a new ProcessingFeeMatrix instance.
     *
     * @param baseFee Base processing fee
     * @param minFee Minimum allowed fee
     * @param maxFee Maximum allowed fee
     * @param adjustmentRange Maximum adjustment range
     * @throws IllegalArgumentException if fee values are invalid
     */
    public ProcessingFeeMatrix(
            double baseFee,
            double minFee,
            double maxFee,
            double adjustmentRange) {
        validateFeeValues(baseFee, minFee, maxFee, adjustmentRange);
        this.baseFee = baseFee;
        this.minFee = minFee;
        this.maxFee = maxFee;
        this.adjustmentRange = adjustmentRange;
        this.constraints = new TermConstraints(minFee, maxFee, baseFee, adjustmentRange);
    }

    @Override
    public TermAdjustment getAdjustment(TermType termType, UserProfile profile) {
        if (termType != TermType.PROCESSING_FEE) {
            throw new IllegalArgumentException(
                "ProcessingFeeMatrix can only adjust processing fee terms"
            );
        }

        double sensitivity = profile.getPriceSensitivity().getSensitivity();
        double confidence = profile.getPriceSensitivity().getConfidence();
        double longTermValue = profile.getLongTermValue();

        // Calculate adjustment based on user profile
        double adjustment = calculateAdjustment(sensitivity, confidence, longTermValue);
        double adjustedValue = Math.min(Math.max(baseFee + adjustment, minFee), maxFee);
        double impact = calculateImpact(adjustment);

        return new TermAdjustment(termType, baseFee, adjustedValue, impact);
    }

    @Override
    public boolean validateAdjustment(TermAdjustment adjustment) {
        if (adjustment.getTermType() != TermType.PROCESSING_FEE) {
            return false;
        }

        return constraints.isInRange(adjustment.getAdjustedValue()) &&
               constraints.isAdjustmentValid(adjustment.getAdjustedValue());
    }

    @Override
    public TermConstraints getConstraints() {
        return constraints;
    }

    /**
     * Calculates the processing fee adjustment based on user profile factors.
     *
     * @param sensitivity User's price sensitivity
     * @param confidence Confidence in the sensitivity calculation
     * @param longTermValue User's long-term value
     * @return Calculated adjustment
     */
    private double calculateAdjustment(
            double sensitivity,
            double confidence,
            double longTermValue) {
        // Higher sensitivity = lower fee
        double sensitivityAdjustment = -sensitivity * adjustmentRange;

        // Higher confidence = larger adjustment
        double confidenceFactor = confidence;

        // Higher long-term value = smaller fee reduction
        double valueFactor = Math.min(1.0, longTermValue / 1000000.0);

        return sensitivityAdjustment * confidenceFactor * (1 - valueFactor);
    }

    /**
     * Calculates the impact of a processing fee adjustment.
     *
     * @param adjustment Fee adjustment
     * @return Impact value
     */
    private double calculateImpact(double adjustment) {
        // Impact is proportional to the absolute adjustment size
        return Math.abs(adjustment) / adjustmentRange;
    }

    /**
     * Validates processing fee values.
     *
     * @param baseFee Base processing fee
     * @param minFee Minimum allowed fee
     * @param maxFee Maximum allowed fee
     * @param adjustmentRange Maximum adjustment range
     * @throws IllegalArgumentException if values are invalid
     */
    private void validateFeeValues(
            double baseFee,
            double minFee,
            double maxFee,
            double adjustmentRange) {
        if (minFee > maxFee) {
            throw new IllegalArgumentException(
                "Minimum fee cannot be greater than maximum fee"
            );
        }

        if (baseFee < minFee || baseFee > maxFee) {
            throw new IllegalArgumentException(
                "Base fee must be between minimum and maximum fee"
            );
        }

        if (adjustmentRange < 0) {
            throw new IllegalArgumentException(
                "Adjustment range cannot be negative"
            );
        }

        if (baseFee - adjustmentRange < minFee || baseFee + adjustmentRange > maxFee) {
            throw new IllegalArgumentException(
                "Adjustment range exceeds minimum or maximum fee"
            );
        }
    }
} 