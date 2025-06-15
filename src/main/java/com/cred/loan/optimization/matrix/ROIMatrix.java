package com.cred.loan.optimization.matrix;

import com.cred.loan.optimization.model.TermAdjustment;
import com.cred.loan.optimization.model.TermConstraints;
import com.cred.loan.optimization.model.TermType;
import com.cred.loan.optimization.model.UserProfile;

/**
 * Matrix for calculating ROI adjustments based on user profiles and business rules.
 */
public class ROIMatrix implements TermAdjustmentMatrix {
    private final double baseROI;
    private final double minROI;
    private final double maxROI;
    private final double riskRange;
    private final TermConstraints constraints;

    /**
     * Creates a new ROIMatrix instance.
     *
     * @param baseROI Base ROI value
     * @param minROI Minimum allowed ROI
     * @param maxROI Maximum allowed ROI
     * @param riskRange Maximum adjustment range based on risk
     * @throws IllegalArgumentException if ROI values are invalid
     */
    public ROIMatrix(double baseROI, double minROI, double maxROI, double riskRange) {
        validateROIValues(baseROI, minROI, maxROI, riskRange);
        this.baseROI = baseROI;
        this.minROI = minROI;
        this.maxROI = maxROI;
        this.riskRange = riskRange;
        this.constraints = new TermConstraints(minROI, maxROI, baseROI, riskRange);
    }

    @Override
    public TermAdjustment getAdjustment(TermType termType, UserProfile profile) {
        if (termType != TermType.ROI) {
            throw new IllegalArgumentException("ROIMatrix can only adjust ROI terms");
        }

        double sensitivity = profile.getPriceSensitivity().getSensitivity();
        double confidence = profile.getPriceSensitivity().getConfidence();
        double conversionProb = profile.getConversionProbability();

        // Calculate adjustment based on user profile
        double adjustment = calculateAdjustment(sensitivity, confidence, conversionProb);
        double adjustedValue = Math.min(Math.max(baseROI + adjustment, minROI), maxROI);
        double impact = calculateImpact(adjustment);

        return new TermAdjustment(termType, baseROI, adjustedValue, impact);
    }

    @Override
    public boolean validateAdjustment(TermAdjustment adjustment) {
        if (adjustment.getTermType() != TermType.ROI) {
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
     * Calculates the ROI adjustment based on user profile factors.
     *
     * @param sensitivity User's price sensitivity
     * @param confidence Confidence in the sensitivity calculation
     * @param conversionProb Probability of conversion
     * @return Calculated adjustment
     */
    private double calculateAdjustment(
            double sensitivity,
            double confidence,
            double conversionProb) {
        // Higher sensitivity = lower ROI
        double sensitivityAdjustment = -sensitivity * riskRange;

        // Higher confidence = larger adjustment
        double confidenceFactor = confidence;

        // Higher conversion probability = smaller adjustment
        double conversionFactor = 1 - conversionProb;

        return sensitivityAdjustment * confidenceFactor * conversionFactor;
    }

    /**
     * Calculates the impact of an ROI adjustment.
     *
     * @param adjustment ROI adjustment
     * @return Impact value
     */
    private double calculateImpact(double adjustment) {
        // Impact is proportional to the absolute adjustment size
        return Math.abs(adjustment) / riskRange;
    }

    /**
     * Validates ROI values.
     *
     * @param baseROI Base ROI value
     * @param minROI Minimum allowed ROI
     * @param maxROI Maximum allowed ROI
     * @param riskRange Maximum adjustment range
     * @throws IllegalArgumentException if values are invalid
     */
    private void validateROIValues(
            double baseROI,
            double minROI,
            double maxROI,
            double riskRange) {
        if (minROI > maxROI) {
            throw new IllegalArgumentException(
                "Minimum ROI cannot be greater than maximum ROI"
            );
        }

        if (baseROI < minROI || baseROI > maxROI) {
            throw new IllegalArgumentException(
                "Base ROI must be between minimum and maximum ROI"
            );
        }

        if (riskRange < 0) {
            throw new IllegalArgumentException(
                "Risk range cannot be negative"
            );
        }

        if (baseROI - riskRange < minROI || baseROI + riskRange > maxROI) {
            throw new IllegalArgumentException(
                "Risk range exceeds minimum or maximum ROI"
            );
        }
    }
} 