package com.cred.loan.risk.model;

/**
 * Represents a range of Return on Investment (ROI) values with minimum, maximum,
 * base, and step values for loan offers.
 */
public class ROIRange {
    private final double min;
    private final double max;
    private final double base;
    private final double step;

    /**
     * Creates a new ROIRange instance.
     *
     * @param min Minimum ROI value
     * @param max Maximum ROI value
     * @param base Base ROI value
     * @param step Step size for ROI adjustments
     * @throws IllegalArgumentException if min > max or base is not within range
     */
    public ROIRange(double min, double max, double base, double step) {
        if (min > max) {
            throw new IllegalArgumentException("Minimum ROI cannot be greater than maximum ROI");
        }
        if (base < min || base > max) {
            throw new IllegalArgumentException("Base ROI must be within the min-max range");
        }
        if (step <= 0) {
            throw new IllegalArgumentException("Step size must be positive");
        }

        this.min = min;
        this.max = max;
        this.base = base;
        this.step = step;
    }

    /**
     * Checks if a given ROI value is within this range.
     *
     * @param roi The ROI value to check
     * @return true if the ROI is within range, false otherwise
     */
    public boolean isInRange(Double roi) {
        return roi != null && roi >= min && roi <= max;
    }

    /**
     * Gets the minimum ROI value.
     *
     * @return The minimum ROI
     */
    public double getMin() {
        return min;
    }

    /**
     * Gets the maximum ROI value.
     *
     * @return The maximum ROI
     */
    public double getMax() {
        return max;
    }

    /**
     * Gets the base ROI value.
     *
     * @return The base ROI
     */
    public double getBase() {
        return base;
    }

    /**
     * Gets the step size for ROI adjustments.
     *
     * @return The step size
     */
    public double getStep() {
        return step;
    }

    /**
     * Rounds a given ROI value to the nearest step within the range.
     *
     * @param roi The ROI value to round
     * @return The rounded ROI value
     */
    public double roundToStep(double roi) {
        if (!isInRange(roi)) {
            throw new IllegalArgumentException("ROI value is outside the valid range");
        }
        double steps = Math.round((roi - min) / step);
        return Math.min(max, min + (steps * step));
    }
} 