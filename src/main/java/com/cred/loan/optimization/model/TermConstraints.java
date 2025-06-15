package com.cred.loan.optimization.model;

/**
 * Represents the constraints for adjusting a loan term, including minimum and
 * maximum allowed values, base value, and adjustment range.
 */
public class TermConstraints {
    private final double minValue;
    private final double maxValue;
    private final double baseValue;
    private final double adjustmentRange;

    /**
     * Creates a new TermConstraints instance.
     *
     * @param minValue Minimum allowed value
     * @param maxValue Maximum allowed value
     * @param baseValue Base value for the term
     * @param adjustmentRange Maximum allowed adjustment range
     * @throws IllegalArgumentException if constraints are invalid
     */
    public TermConstraints(double minValue, double maxValue, double baseValue, double adjustmentRange) {
        validateConstraints(minValue, maxValue, baseValue, adjustmentRange);
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.baseValue = baseValue;
        this.adjustmentRange = adjustmentRange;
    }

    /**
     * Gets the minimum allowed value.
     *
     * @return Minimum value
     */
    public double getMinValue() {
        return minValue;
    }

    /**
     * Gets the maximum allowed value.
     *
     * @return Maximum value
     */
    public double getMaxValue() {
        return maxValue;
    }

    /**
     * Gets the base value for the term.
     *
     * @return Base value
     */
    public double getBaseValue() {
        return baseValue;
    }

    /**
     * Gets the maximum allowed adjustment range.
     *
     * @return Adjustment range
     */
    public double getAdjustmentRange() {
        return adjustmentRange;
    }

    /**
     * Validates if a value is within the allowed range.
     *
     * @param value Value to validate
     * @return true if the value is within range, false otherwise
     */
    public boolean isInRange(double value) {
        return value >= minValue && value <= maxValue;
    }

    /**
     * Validates if an adjustment is within the allowed range.
     *
     * @param adjustment Adjustment to validate
     * @return true if the adjustment is within range, false otherwise
     */
    public boolean isAdjustmentValid(double adjustment) {
        return Math.abs(adjustment - baseValue) <= adjustmentRange;
    }

    /**
     * Validates the constraint values.
     *
     * @param minValue Minimum allowed value
     * @param maxValue Maximum allowed value
     * @param baseValue Base value for the term
     * @param adjustmentRange Maximum allowed adjustment range
     * @throws IllegalArgumentException if constraints are invalid
     */
    private void validateConstraints(
            double minValue,
            double maxValue,
            double baseValue,
            double adjustmentRange) {
        if (minValue > maxValue) {
            throw new IllegalArgumentException(
                "Minimum value cannot be greater than maximum value"
            );
        }

        if (baseValue < minValue || baseValue > maxValue) {
            throw new IllegalArgumentException(
                "Base value must be between minimum and maximum values"
            );
        }

        if (adjustmentRange < 0) {
            throw new IllegalArgumentException(
                "Adjustment range cannot be negative"
            );
        }

        if (baseValue - adjustmentRange < minValue || baseValue + adjustmentRange > maxValue) {
            throw new IllegalArgumentException(
                "Adjustment range exceeds minimum or maximum values"
            );
        }
    }

    @Override
    public String toString() {
        return String.format(
            "TermConstraints{minValue=%.2f, maxValue=%.2f, baseValue=%.2f, adjustmentRange=%.2f}",
            minValue, maxValue, baseValue, adjustmentRange
        );
    }
} 