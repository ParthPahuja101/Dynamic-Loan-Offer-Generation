package com.cred.loan.core.service.impl;

import com.cred.loan.core.model.*;
import com.cred.loan.core.service.OfferOptimizationService;
import com.cred.loan.core.exception.OptimizationException;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

/**
 * Implementation of the OfferOptimizationService interface.
 * This service optimizes loan offers based on risk assessment and user behavior analysis.
 */
@Service
public class OfferOptimizationServiceImpl implements OfferOptimizationService {

    private static final double MAX_ROI_ADJUSTMENT = 0.25;
    private static final double MAX_PROCESSING_FEE_ADJUSTMENT = 0.1;
    private static final double MAX_TENURE_ADJUSTMENT = 0.2;

    @Override
    public CompletableFuture<List<OptimizedOffer>> optimizeOffers(
            List<BaseOffer> baseOffers,
            RiskAssessmentResult riskAssessment,
            BehaviorAnalysisResult behaviorAnalysis) {
        List<CompletableFuture<OptimizedOffer>> futures = baseOffers.stream()
            .map(offer -> optimizeOffer(offer, riskAssessment, behaviorAnalysis))
            .collect(Collectors.toList());

        return CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]))
            .thenApply(v -> futures.stream()
                .map(CompletableFuture::join)
                .collect(Collectors.toList()));
    }

    @Override
    public CompletableFuture<OptimizedOffer> optimizeOffer(
            BaseOffer baseOffer,
            RiskAssessmentResult riskAssessment,
            BehaviorAnalysisResult behaviorAnalysis) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                // Calculate adjustments based on risk and behavior
                double roiAdjustment = calculateROIAdjustment(baseOffer, riskAssessment, behaviorAnalysis);
                double processingFeeAdjustment = calculateProcessingFeeAdjustment(baseOffer, riskAssessment, behaviorAnalysis);
                int tenureAdjustment = calculateTenureAdjustment(baseOffer, riskAssessment, behaviorAnalysis);

                // Apply adjustments
                double adjustedROI = baseOffer.getROI() + roiAdjustment;
                double adjustedProcessingFee = baseOffer.getProcessingFee() * (1 + processingFeeAdjustment);
                int adjustedTenure = baseOffer.getTenure() + tenureAdjustment;

                // Calculate risk impact
                double riskImpact = calculateRiskImpact(
                    roiAdjustment,
                    processingFeeAdjustment,
                    tenureAdjustment,
                    riskAssessment
                );

                // Create optimized offer
                return new OptimizedOfferImpl(
                    baseOffer,
                    adjustedROI,
                    adjustedProcessingFee,
                    adjustedTenure,
                    riskImpact,
                    behaviorAnalysis.getConversionProbability()
                );
            } catch (Exception e) {
                throw new OptimizationException("Error optimizing offer: " + e.getMessage(), e);
            }
        });
    }

    /**
     * Calculates the ROI adjustment based on risk assessment and behavior analysis.
     *
     * @param baseOffer The base offer
     * @param riskAssessment The risk assessment result
     * @param behaviorAnalysis The behavior analysis result
     * @return The ROI adjustment
     */
    private double calculateROIAdjustment(
            BaseOffer baseOffer,
            RiskAssessmentResult riskAssessment,
            BehaviorAnalysisResult behaviorAnalysis) {
        double adjustment = 0.0;

        // Adjust based on risk level
        switch (riskAssessment.getRiskLevel()) {
            case LOW -> adjustment -= 0.1;
            case MEDIUM -> adjustment += 0.0;
            case HIGH -> adjustment += 0.2;
        }

        // Adjust based on price sensitivity
        PriceSensitivity priceSensitivity = behaviorAnalysis.getPriceSensitivity();
        adjustment += (1 - priceSensitivity.getSensitivity()) * 0.15;

        // Adjust based on conversion probability
        double conversionProbability = behaviorAnalysis.getConversionProbability();
        if (conversionProbability > 0.7) {
            adjustment -= 0.1;
        } else if (conversionProbability < 0.3) {
            adjustment += 0.1;
        }

        // Ensure adjustment is within bounds
        return Math.min(Math.max(adjustment, -MAX_ROI_ADJUSTMENT), MAX_ROI_ADJUSTMENT);
    }

    /**
     * Calculates the processing fee adjustment based on risk assessment and behavior analysis.
     *
     * @param baseOffer The base offer
     * @param riskAssessment The risk assessment result
     * @param behaviorAnalysis The behavior analysis result
     * @return The processing fee adjustment
     */
    private double calculateProcessingFeeAdjustment(
            BaseOffer baseOffer,
            RiskAssessmentResult riskAssessment,
            BehaviorAnalysisResult behaviorAnalysis) {
        double adjustment = 0.0;

        // Adjust based on risk level
        switch (riskAssessment.getRiskLevel()) {
            case LOW -> adjustment -= 0.05;
            case MEDIUM -> adjustment += 0.0;
            case HIGH -> adjustment += 0.1;
        }

        // Adjust based on price sensitivity
        PriceSensitivity priceSensitivity = behaviorAnalysis.getPriceSensitivity();
        adjustment += (1 - priceSensitivity.getSensitivity()) * 0.05;

        // Ensure adjustment is within bounds
        return Math.min(Math.max(adjustment, -MAX_PROCESSING_FEE_ADJUSTMENT), MAX_PROCESSING_FEE_ADJUSTMENT);
    }

    /**
     * Calculates the tenure adjustment based on risk assessment and behavior analysis.
     *
     * @param baseOffer The base offer
     * @param riskAssessment The risk assessment result
     * @param behaviorAnalysis The behavior analysis result
     * @return The tenure adjustment
     */
    private int calculateTenureAdjustment(
            BaseOffer baseOffer,
            RiskAssessmentResult riskAssessment,
            BehaviorAnalysisResult behaviorAnalysis) {
        int adjustment = 0;

        // Adjust based on risk level
        switch (riskAssessment.getRiskLevel()) {
            case LOW -> adjustment += 3;
            case MEDIUM -> adjustment += 0;
            case HIGH -> adjustment -= 3;
        }

        // Adjust based on long-term value
        double longTermValue = behaviorAnalysis.getLongTermValue();
        if (longTermValue > 0.8) {
            adjustment += 3;
        } else if (longTermValue < 0.3) {
            adjustment -= 3;
        }

        // Ensure adjustment is within bounds
        int maxAdjustment = (int) (baseOffer.getTenure() * MAX_TENURE_ADJUSTMENT);
        return Math.min(Math.max(adjustment, -maxAdjustment), maxAdjustment);
    }

    /**
     * Calculates the risk impact of the adjustments.
     *
     * @param roiAdjustment The ROI adjustment
     * @param processingFeeAdjustment The processing fee adjustment
     * @param tenureAdjustment The tenure adjustment
     * @param riskAssessment The risk assessment result
     * @return The risk impact score (0.0 to 1.0)
     */
    private double calculateRiskImpact(
            double roiAdjustment,
            double processingFeeAdjustment,
            int tenureAdjustment,
            RiskAssessmentResult riskAssessment) {
        double impact = 0.0;

        // ROI impact
        impact += Math.abs(roiAdjustment) * 0.4;

        // Processing fee impact
        impact += Math.abs(processingFeeAdjustment) * 0.3;

        // Tenure impact
        impact += Math.abs(tenureAdjustment) * 0.3;

        // Adjust based on risk level
        switch (riskAssessment.getRiskLevel()) {
            case LOW -> impact *= 0.8;
            case MEDIUM -> impact *= 1.0;
            case HIGH -> impact *= 1.2;
        }

        return Math.min(Math.max(impact, 0.0), 1.0);
    }
}

/**
 * Implementation of the OptimizedOffer interface.
 */
class OptimizedOfferImpl implements OptimizedOffer {
    private final BaseOffer baseOffer;
    private final Double adjustedROI;
    private final Double adjustedProcessingFee;
    private final Integer adjustedTenure;
    private final Double riskImpact;
    private final Double conversionProbability;

    public OptimizedOfferImpl(
            BaseOffer baseOffer,
            Double adjustedROI,
            Double adjustedProcessingFee,
            Integer adjustedTenure,
            Double riskImpact,
            Double conversionProbability) {
        this.baseOffer = baseOffer;
        this.adjustedROI = adjustedROI;
        this.adjustedProcessingFee = adjustedProcessingFee;
        this.adjustedTenure = adjustedTenure;
        this.riskImpact = riskImpact;
        this.conversionProbability = conversionProbability;
    }

    @Override
    public BaseOffer getBaseOffer() {
        return baseOffer;
    }

    @Override
    public Double getAdjustedROI() {
        return adjustedROI;
    }

    @Override
    public Double getRiskImpact() {
        return riskImpact;
    }

    @Override
    public Double getConversionProbability() {
        return conversionProbability;
    }
} 