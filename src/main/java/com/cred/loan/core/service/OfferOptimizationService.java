package com.cred.loan.core.service;

import com.cred.loan.core.model.BaseOffer;
import com.cred.loan.core.model.OptimizedOffer;
import com.cred.loan.core.model.RiskAssessmentResult;
import com.cred.loan.core.model.BehaviorAnalysisResult;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * Service interface for optimizing loan offers.
 * This service applies business rules and user behavior insights to optimize
 * loan offers while maintaining risk constraints.
 */
public interface OfferOptimizationService {
    /**
     * Optimizes a list of base offers based on risk assessment and behavior analysis.
     *
     * @param baseOffers The list of base offers to optimize
     * @param riskAssessment The risk assessment result
     * @param behaviorAnalysis The behavior analysis result
     * @return A CompletableFuture containing the list of optimized offers
     */
    CompletableFuture<List<OptimizedOffer>> optimizeOffers(
        List<BaseOffer> baseOffers,
        RiskAssessmentResult riskAssessment,
        BehaviorAnalysisResult behaviorAnalysis
    );

    /**
     * Optimizes a single base offer.
     *
     * @param baseOffer The base offer to optimize
     * @param riskAssessment The risk assessment result
     * @param behaviorAnalysis The behavior analysis result
     * @return A CompletableFuture containing the optimized offer
     */
    CompletableFuture<OptimizedOffer> optimizeOffer(
        BaseOffer baseOffer,
        RiskAssessmentResult riskAssessment,
        BehaviorAnalysisResult behaviorAnalysis
    );
} 