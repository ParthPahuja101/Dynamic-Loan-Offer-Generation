package com.cred.loan.optimization.matrix;

import com.cred.loan.optimization.model.TermAdjustment;
import com.cred.loan.optimization.model.TermConstraints;
import com.cred.loan.optimization.model.TermType;
import com.cred.loan.optimization.model.UserProfile;

import java.util.List;

/**
 * Matrix for calculating loan amount adjustments based on user profiles
 * and business rules.
 */
public class LoanAmountMatrix implements TermAdjustmentMatrix {
    private final List<Double> availableAmounts;
    private final double baseAmount;
    private final double minAmount;
    private final double maxAmount;
    private final double adjustmentRange;
    private final TermConstraints constraints;

    /**
     * Creates a new LoanAmountMatrix instance.
     *
     * @param availableAmounts List of available loan amount options
     * @param baseAmount Base loan amount value
     * @param minAmount Minimum allowed loan amount
     * @param maxAmount Maximum allowed loan amount
     * @param adjustmentRange Maximum adjustment range
     * @throws IllegalArgumentException if amount values are invalid
     */
    public LoanAmountMatrix(
            List<Double> availableAmounts,
            double baseAmount,
            double minAmount,
            double maxAmount,
            double adjustmentRange) {
        validateAmountValues(availableAmounts, baseAmount, minAmount, maxAmount, adjustmentRange);
        this.availableAmounts = availableAmounts;
        this.baseAmount = baseAmount;
        this.minAmount = minAmount;
        this.maxAmount = maxAmount;
        this.adjustmentRange = adjustmentRange;
        this.constraints = new TermConstraints(minAmount, maxAmount, baseAmount, adjustmentRange);
    }

    @Override
    public TermAdjustment getAdjustment(TermType termType, UserProfile profile) {
        if (termType != TermType.LOAN_AMOUNT) {
            throw new IllegalArgumentException(
                "LoanAmountMatrix can only adjust loan amount terms"
            );
        }

        double sensitivity = profile.getPriceSensitivity().getSensitivity();
        double confidence = profile.getPriceSensitivity().getConfidence();
        double longTermValue = profile.getLongTermValue();

        // Calculate adjustment based on user profile
        double adjustment = calculateAdjustment(sensitivity, confidence, longTermValue);
        double adjustedValue = findNearestAvailableAmount(baseAmount + adjustment);
        double impact = calculateImpact(adjustment);

        return new TermAdjustment(termType, baseAmount, adjustedValue, impact);
    }

    @Override
    public boolean validateAdjustment(TermAdjustment adjustment) {
        if (adjustment.getTermType() != TermType.LOAN_AMOUNT) {
            return false;
        }

        double adjustedValue = adjustment.getAdjustedValue();
        return constraints.isInRange(adjustedValue) &&
               constraints.isAdjustmentValid(adjustedValue) &&
               availableAmounts.contains(adjustedValue);
    }

    @Override
    public TermConstraints getConstraints() {
        return constraints;
    }

    /**
     * Calculates the loan amount adjustment based on user profile factors.
     *
     * @param sensitivity User's price sensitivity
     * @param confidence Confidence in the sensitivity calculation
     * @param longTermValue User's long-term value
     * @return Calculated adjustment amount
     */
    private double calculateAdjustment(
            double sensitivity,
            double confidence,
            double longTermValue) {
        // Higher sensitivity = lower amount
        double sensitivityAdjustment = -sensitivity * adjustmentRange;

        // Higher confidence = larger adjustment
        double confidenceFactor = confidence;

        // Higher long-term value = higher amount
        double valueFactor = Math.min(1.0, longTermValue / 1000000.0);

        return sensitivityAdjustment * confidenceFactor * (1 - valueFactor);
    }

    /**
     * Finds the nearest available loan amount option.
     *
     * @param targetAmount Target loan amount
     * @return Nearest available loan amount
     */
    private double findNearestAvailableAmount(double targetAmount) {
        return availableAmounts.stream()
            .min((a, b) -> Double.compare(
                Math.abs(a - targetAmount),
                Math.abs(b - targetAmount)
            ))
            .orElse(baseAmount);
    }

    /**
     * Calculates the impact of a loan amount adjustment.
     *
     * @param adjustment Loan amount adjustment
     * @return Impact value
     */
    private double calculateImpact(double adjustment) {
        // Impact is proportional to the absolute adjustment size
        return Math.abs(adjustment) / adjustmentRange;
    }

    /**
     * Validates loan amount values.
     *
     * @param availableAmounts List of available loan amount options
     * @param baseAmount Base loan amount value
     * @param minAmount Minimum allowed loan amount
     * @param maxAmount Maximum allowed loan amount
     * @param adjustmentRange Maximum adjustment range
     * @throws IllegalArgumentException if values are invalid
     */
    private void validateAmountValues(
            List<Double> availableAmounts,
            double baseAmount,
            double minAmount,
            double maxAmount,
            double adjustmentRange) {
        if (availableAmounts == null || availableAmounts.isEmpty()) {
            throw new IllegalArgumentException(
                "Available amounts list cannot be null or empty"
            );
        }

        if (minAmount > maxAmount) {
            throw new IllegalArgumentException(
                "Minimum amount cannot be greater than maximum amount"
            );
        }

        if (baseAmount < minAmount || baseAmount > maxAmount) {
            throw new IllegalArgumentException(
                "Base amount must be between minimum and maximum amount"
            );
        }

        if (adjustmentRange < 0) {
            throw new IllegalArgumentException(
                "Adjustment range cannot be negative"
            );
        }

        if (!availableAmounts.contains(baseAmount)) {
            throw new IllegalArgumentException(
                "Base amount must be in the available amounts list"
            );
        }

        if (availableAmounts.stream().anyMatch(a -> a < minAmount || a > maxAmount)) {
            throw new IllegalArgumentException(
                "All available amounts must be between minimum and maximum amount"
            );
        }
    }
} 