package com.cred.loan.optimization.matrix;

import com.cred.loan.optimization.model.TermType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Factory class for creating and configuring term adjustment matrices.
 * This class is responsible for creating and initializing different types
 * of matrices based on business rules and configuration parameters.
 */
public class MatrixConfigurationFactory {
    private final Map<TermType, TermAdjustmentMatrix> matrices;

    /**
     * Creates a new MatrixConfigurationFactory instance.
     *
     * @param roiConfig ROI matrix configuration
     * @param processingFeeConfig Processing fee matrix configuration
     * @param tenureConfig Tenure matrix configuration
     * @param loanAmountConfig Loan amount matrix configuration
     */
    public MatrixConfigurationFactory(
            ROIMatrixConfig roiConfig,
            ProcessingFeeMatrixConfig processingFeeConfig,
            TenureMatrixConfig tenureConfig,
            LoanAmountMatrixConfig loanAmountConfig) {
        this.matrices = new HashMap<>();
        initializeMatrices(roiConfig, processingFeeConfig, tenureConfig, loanAmountConfig);
    }

    /**
     * Gets the matrix for a specific term type.
     *
     * @param termType Type of term to get matrix for
     * @return TermAdjustmentMatrix for the specified term type
     * @throws IllegalArgumentException if no matrix exists for the term type
     */
    public TermAdjustmentMatrix getMatrix(TermType termType) {
        TermAdjustmentMatrix matrix = matrices.get(termType);
        if (matrix == null) {
            throw new IllegalArgumentException(
                "No matrix configuration found for term type: " + termType
            );
        }
        return matrix;
    }

    /**
     * Initializes all matrices with their respective configurations.
     *
     * @param roiConfig ROI matrix configuration
     * @param processingFeeConfig Processing fee matrix configuration
     * @param tenureConfig Tenure matrix configuration
     * @param loanAmountConfig Loan amount matrix configuration
     */
    private void initializeMatrices(
            ROIMatrixConfig roiConfig,
            ProcessingFeeMatrixConfig processingFeeConfig,
            TenureMatrixConfig tenureConfig,
            LoanAmountMatrixConfig loanAmountConfig) {
        matrices.put(TermType.ROI, createROIMatrix(roiConfig));
        matrices.put(TermType.PROCESSING_FEE, createProcessingFeeMatrix(processingFeeConfig));
        matrices.put(TermType.TENURE, createTenureMatrix(tenureConfig));
        matrices.put(TermType.LOAN_AMOUNT, createLoanAmountMatrix(loanAmountConfig));
    }

    /**
     * Creates a new ROI matrix with the specified configuration.
     *
     * @param config ROI matrix configuration
     * @return Configured ROI matrix
     */
    private ROIMatrix createROIMatrix(ROIMatrixConfig config) {
        return new ROIMatrix(
            config.getBaseROI(),
            config.getMinROI(),
            config.getMaxROI(),
            config.getRiskRange()
        );
    }

    /**
     * Creates a new processing fee matrix with the specified configuration.
     *
     * @param config Processing fee matrix configuration
     * @return Configured processing fee matrix
     */
    private ProcessingFeeMatrix createProcessingFeeMatrix(ProcessingFeeMatrixConfig config) {
        return new ProcessingFeeMatrix(
            config.getBaseFee(),
            config.getMinFee(),
            config.getMaxFee(),
            config.getAdjustmentRange()
        );
    }

    /**
     * Creates a new tenure matrix with the specified configuration.
     *
     * @param config Tenure matrix configuration
     * @return Configured tenure matrix
     */
    private TenureMatrix createTenureMatrix(TenureMatrixConfig config) {
        return new TenureMatrix(
            config.getAvailableTenures(),
            config.getBaseTenure(),
            config.getMinTenure(),
            config.getMaxTenure(),
            config.getAdjustmentRange()
        );
    }

    /**
     * Creates a new loan amount matrix with the specified configuration.
     *
     * @param config Loan amount matrix configuration
     * @return Configured loan amount matrix
     */
    private LoanAmountMatrix createLoanAmountMatrix(LoanAmountMatrixConfig config) {
        return new LoanAmountMatrix(
            config.getAvailableAmounts(),
            config.getBaseAmount(),
            config.getMinAmount(),
            config.getMaxAmount(),
            config.getAdjustmentRange()
        );
    }

    /**
     * Configuration class for ROI matrix.
     */
    public static class ROIMatrixConfig {
        private final double baseROI;
        private final double minROI;
        private final double maxROI;
        private final double riskRange;

        public ROIMatrixConfig(double baseROI, double minROI, double maxROI, double riskRange) {
            this.baseROI = baseROI;
            this.minROI = minROI;
            this.maxROI = maxROI;
            this.riskRange = riskRange;
        }

        public double getBaseROI() { return baseROI; }
        public double getMinROI() { return minROI; }
        public double getMaxROI() { return maxROI; }
        public double getRiskRange() { return riskRange; }
    }

    /**
     * Configuration class for processing fee matrix.
     */
    public static class ProcessingFeeMatrixConfig {
        private final double baseFee;
        private final double minFee;
        private final double maxFee;
        private final double adjustmentRange;

        public ProcessingFeeMatrixConfig(
                double baseFee,
                double minFee,
                double maxFee,
                double adjustmentRange) {
            this.baseFee = baseFee;
            this.minFee = minFee;
            this.maxFee = maxFee;
            this.adjustmentRange = adjustmentRange;
        }

        public double getBaseFee() { return baseFee; }
        public double getMinFee() { return minFee; }
        public double getMaxFee() { return maxFee; }
        public double getAdjustmentRange() { return adjustmentRange; }
    }

    /**
     * Configuration class for tenure matrix.
     */
    public static class TenureMatrixConfig {
        private final List<Integer> availableTenures;
        private final int baseTenure;
        private final int minTenure;
        private final int maxTenure;
        private final double adjustmentRange;

        public TenureMatrixConfig(
                List<Integer> availableTenures,
                int baseTenure,
                int minTenure,
                int maxTenure,
                double adjustmentRange) {
            this.availableTenures = availableTenures;
            this.baseTenure = baseTenure;
            this.minTenure = minTenure;
            this.maxTenure = maxTenure;
            this.adjustmentRange = adjustmentRange;
        }

        public List<Integer> getAvailableTenures() { return availableTenures; }
        public int getBaseTenure() { return baseTenure; }
        public int getMinTenure() { return minTenure; }
        public int getMaxTenure() { return maxTenure; }
        public double getAdjustmentRange() { return adjustmentRange; }
    }

    /**
     * Configuration class for loan amount matrix.
     */
    public static class LoanAmountMatrixConfig {
        private final List<Double> availableAmounts;
        private final double baseAmount;
        private final double minAmount;
        private final double maxAmount;
        private final double adjustmentRange;

        public LoanAmountMatrixConfig(
                List<Double> availableAmounts,
                double baseAmount,
                double minAmount,
                double maxAmount,
                double adjustmentRange) {
            this.availableAmounts = availableAmounts;
            this.baseAmount = baseAmount;
            this.minAmount = minAmount;
            this.maxAmount = maxAmount;
            this.adjustmentRange = adjustmentRange;
        }

        public List<Double> getAvailableAmounts() { return availableAmounts; }
        public double getBaseAmount() { return baseAmount; }
        public double getMinAmount() { return minAmount; }
        public double getMaxAmount() { return maxAmount; }
        public double getAdjustmentRange() { return adjustmentRange; }
    }
} 