package com.cred.loan.optimization.model;

/**
 * Represents an adjustment made to a loan term during the optimization process.
 */
public class TermAdjustment {
    private final TermType termType;
    private final double baseValue;
    private final double adjustedValue;
    private final double impact;

    /**
     * Creates a new TermAdjustment instance.
     *
     * @param termType Type of term being adjusted
     * @param baseValue Original value before adjustment
     * @param adjustedValue New value after adjustment
     * @param impact Impact of the adjustment on risk/value
     */
    public TermAdjustment(TermType termType, double baseValue, double adjustedValue, double impact) {
        this.termType = termType;
        this.baseValue = baseValue;
        this.adjustedValue = adjustedValue;
        this.impact = impact;
    }

    /**
     * Gets the type of term being adjusted.
     *
     * @return Term type
     */
    public TermType getTermType() {
        return termType;
    }

    /**
     * Gets the original value before adjustment.
     *
     * @return Base value
     */
    public double getBaseValue() {
        return baseValue;
    }

    /**
     * Gets the new value after adjustment.
     *
     * @return Adjusted value
     */
    public double getAdjustedValue() {
        return adjustedValue;
    }

    /**
     * Gets the impact of the adjustment on risk/value.
     *
     * @return Impact value
     */
    public double getImpact() {
        return impact;
    }

    /**
     * Calculates the percentage change from base to adjusted value.
     *
     * @return Percentage change
     */
    public double getPercentageChange() {
        if (baseValue == 0) {
            return 0;
        }
        return ((adjustedValue - baseValue) / baseValue) * 100;
    }

    @Override
    public String toString() {
        return String.format(
            "TermAdjustment{termType=%s, baseValue=%.2f, adjustedValue=%.2f, impact=%.2f}",
            termType, baseValue, adjustedValue, impact
        );
    }
} 