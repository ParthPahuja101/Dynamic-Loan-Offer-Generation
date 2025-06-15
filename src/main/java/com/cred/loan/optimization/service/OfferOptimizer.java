package com.cred.loan.optimization.service;

import com.cred.loan.behavior.model.BehaviorAnalysisResult;
import com.cred.loan.core.model.BaseOffer;
import com.cred.loan.core.model.OptimizedOffer;
import com.cred.loan.core.model.impl.OptimizedOfferImpl;
import com.cred.loan.optimization.matrix.MatrixConfigurationFactory;
import com.cred.loan.optimization.matrix.TermAdjustmentMatrix;
import com.cred.loan.optimization.model.TermAdjustment;
import com.cred.loan.optimization.model.TermType;
import com.cred.loan.optimization.model.UserProfile;
import com.cred.loan.risk.model.RiskImpact;
import com.cred.loan.risk.model.RiskAssessmentResult;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

/**
 * Service for optimizing loan offers based on risk assessment and behavior analysis.
 */
public class OfferOptimizer {
    private final MatrixConfigurationFactory matrixFactory;

    /**
     * Creates a new OfferOptimizer instance.
     *
     * @param matrixFactory Factory for creating term adjustment matrices
     */
    public OfferOptimizer(MatrixConfigurationFactory matrixFactory) {
        this.matrixFactory = matrixFactory;
    }

    /**
     * Optimizes a list of base offers based on risk assessment and behavior analysis.
     *
     * @param baseOffers List of base offers to optimize
     * @param riskAssessment Risk assessment results
     * @param behaviorAnalysis Behavior analysis results
     * @return CompletableFuture containing the list of optimized offers
     */
    public CompletableFuture<List<OptimizedOffer>> optimizeOffers(
            List<BaseOffer> baseOffers,
            RiskAssessmentResult riskAssessment,
            BehaviorAnalysisResult behaviorAnalysis) {
        return CompletableFuture.supplyAsync(() ->
            baseOffers.stream()
                .map(offer -> optimizeOffer(offer, riskAssessment, behaviorAnalysis))
                .collect(Collectors.toList())
        );
    }

    /**
     * Optimizes a single offer based on risk assessment and behavior analysis.
     *
     * @param offer Base offer to optimize
     * @param riskAssessment Risk assessment results
     * @param behaviorAnalysis Behavior analysis results
     * @return Optimized offer
     */
    private OptimizedOffer optimizeOffer(
            BaseOffer offer,
            RiskAssessmentResult riskAssessment,
            BehaviorAnalysisResult behaviorAnalysis) {
        UserProfile profile = createUserProfile(behaviorAnalysis);
        
        // Optimize each term
        Map<TermType, TermAdjustment> adjustments = new HashMap<>();
        for (TermType termType : TermType.values()) {
            TermAdjustmentMatrix matrix = matrixFactory.getMatrix(termType);
            if (matrix != null) {
                TermAdjustment adjustment = matrix.getAdjustment(termType, profile);
                if (matrix.validateAdjustment(adjustment)) {
                    adjustments.put(termType, adjustment);
                }
            }
        }

        // Calculate risk impact
        double totalRiskImpact = calculateTotalRiskImpact(adjustments);

        // Create optimized offer
        return new OptimizedOfferImpl(
            offer,
            getAdjustedROI(adjustments),
            Double.valueOf(totalRiskImpact),
            Double.valueOf(behaviorAnalysis.getConversionProbability())
        );
    }

    /**
     * Creates a user profile from behavior analysis results.
     *
     * @param behaviorAnalysis Behavior analysis results
     * @return User profile for optimization
     */
    private UserProfile createUserProfile(BehaviorAnalysisResult behaviorAnalysis) {
        return new UserProfile(
            behaviorAnalysis.getPriceSensitivity(),
            behaviorAnalysis.getConversionProbability(),
            behaviorAnalysis.getLongTermValue()
        );
    }

    /**
     * Calculates the total risk impact of all term adjustments.
     *
     * @param adjustments Map of term adjustments
     * @return Total risk impact
     */
    private double calculateTotalRiskImpact(Map<TermType, TermAdjustment> adjustments) {
        return adjustments.values().stream()
            .mapToDouble(TermAdjustment::getImpact)
            .sum();
    }

    /**
     * Gets the adjusted ROI value from the adjustments map.
     *
     * @param adjustments Map of term adjustments
     * @return Adjusted ROI value
     */
    private Double getAdjustedROI(Map<TermType, TermAdjustment> adjustments) {
        TermAdjustment roiAdjustment = adjustments.get(TermType.ROI);
        return roiAdjustment != null ? roiAdjustment.getAdjustedValue() : null;
    }
} 