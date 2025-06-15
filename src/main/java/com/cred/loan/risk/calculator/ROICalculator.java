package com.cred.loan.risk.calculator;

import com.cred.loan.risk.model.ROIRange;
import com.cred.loan.risk.model.RiskLevel;
import com.cred.loan.risk.model.RiskScore;

import java.util.concurrent.CompletableFuture;

/**
 * Calculates Return on Investment (ROI) ranges based on risk assessment results.
 * This calculator determines appropriate ROI ranges for loan offers while
 * considering risk levels and business constraints.
 */
public class ROICalculator {
    private final double baseROI;
    private final double minROI;
    private final double maxROI;
    private final double riskRangeMultiplier;

    /**
     * Creates a new ROICalculator instance.
     *
     * @param baseROI Base ROI value
     * @param minROI Minimum allowed ROI
     * @param maxROI Maximum allowed ROI
     * @param riskRangeMultiplier Multiplier for risk-based range adjustments
     * @throws IllegalArgumentException if ROI values are invalid
     */
    public ROICalculator(double baseROI, double minROI, double maxROI, double riskRangeMultiplier) {
        validateROIValues(baseROI, minROI, maxROI);
        if (riskRangeMultiplier <= 0) {
            throw new IllegalArgumentException("Risk range multiplier must be positive");
        }

        this.baseROI = baseROI;
        this.minROI = minROI;
        this.maxROI = maxROI;
        this.riskRangeMultiplier = riskRangeMultiplier;
    }

    /**
     * Calculates the ROI range based on a risk score.
     *
     * @param riskScore The risk score to evaluate
     * @return A CompletableFuture containing the calculated ROIRange
     */
    public CompletableFuture<ROIRange> calculateROIRange(RiskScore riskScore) {
        return CompletableFuture.supplyAsync(() -> {
            double range = calculateRange(riskScore.getOverallScore());
            double min = Math.max(baseROI - range, minROI);
            double max = Math.min(baseROI + range, maxROI);
            double step = calculateStep(range);

            return new ROIRange(min, max, baseROI, step);
        });
    }

    /**
     * Validates if a given ROI value is within allowed bounds.
     *
     * @param roi The ROI value to validate
     * @return true if the ROI is valid, false otherwise
     */
    public boolean validateROI(Double roi) {
        return roi != null && roi >= minROI && roi <= maxROI;
    }

    /**
     * Adjusts an ROI value based on risk level.
     *
     * @param roi The ROI value to adjust
     * @param riskLevel The risk level to consider
     * @return The adjusted ROI value
     * @throws IllegalArgumentException if ROI is invalid
     */
    public double adjustROI(Double roi, RiskLevel riskLevel) {
        if (!validateROI(roi)) {
            throw new IllegalArgumentException("Invalid ROI value");
        }

        double adjustment = calculateAdjustment(riskLevel);
        return Math.min(Math.max(roi + adjustment, minROI), maxROI);
    }

    /**
     * Calculates the range for ROI adjustments based on risk score.
     *
     * @param riskScore The risk score (0.0 to 1.0)
     * @return The calculated range
     */
    private double calculateRange(double riskScore) {
        // Higher risk scores result in larger ranges
        return baseROI * riskScore * riskRangeMultiplier;
    }

    /**
     * Calculates the step size for ROI adjustments.
     *
     * @param range The total range
     * @return The step size
     */
    private double calculateStep(double range) {
        // Step size is 1% of the range, with a minimum of 0.1%
        return Math.max(range * 0.01, 0.001);
    }

    /**
     * Calculates the ROI adjustment based on risk level.
     *
     * @param riskLevel The risk level
     * @return The adjustment value
     */
    private double calculateAdjustment(RiskLevel riskLevel) {
        switch (riskLevel) {
            case LOW:
                return -0.005; // Decrease ROI by 0.5% for low risk
            case MEDIUM:
                return 0.0;    // No adjustment for medium risk
            case HIGH:
                return 0.01;   // Increase ROI by 1% for high risk
            default:
                return 0.0;
        }
    }

    private void validateROIValues(double baseROI, double minROI, double maxROI) {
        if (minROI >= maxROI) {
            throw new IllegalArgumentException("Minimum ROI must be less than maximum ROI");
        }
        if (baseROI < minROI || baseROI > maxROI) {
            throw new IllegalArgumentException("Base ROI must be within min-max range");
        }
    }
} 