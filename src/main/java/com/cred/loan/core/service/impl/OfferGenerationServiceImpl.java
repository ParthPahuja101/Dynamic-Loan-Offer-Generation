package com.cred.loan.core.service.impl;

import com.cred.loan.core.model.*;
import com.cred.loan.core.service.OfferGenerationService;
import com.cred.loan.core.service.RiskAssessmentService;
import com.cred.loan.core.service.UserBehaviorService;
import com.cred.loan.core.service.OfferOptimizationService;
import com.cred.loan.core.service.DataService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

/**
 * Implementation of the OfferGenerationService interface.
 * This service orchestrates the loan offer generation process by coordinating
 * between risk assessment, behavior analysis, and optimization services.
 */
@Service
public class OfferGenerationServiceImpl implements OfferGenerationService {

    private final RiskAssessmentService riskService;
    private final UserBehaviorService behaviorService;
    private final OfferOptimizationService optimizationService;
    private final DataService dataService;

    /**
     * Creates a new instance of OfferGenerationServiceImpl.
     *
     * @param riskService The risk assessment service
     * @param behaviorService The user behavior service
     * @param optimizationService The offer optimization service
     * @param dataService The data service
     */
    public OfferGenerationServiceImpl(
            RiskAssessmentService riskService,
            UserBehaviorService behaviorService,
            OfferOptimizationService optimizationService,
            DataService dataService) {
        this.riskService = riskService;
        this.behaviorService = behaviorService;
        this.optimizationService = optimizationService;
        this.dataService = dataService;
    }

    @Override
    public CompletableFuture<LoanOfferResponse> generateOffers(LoanOfferRequest request) {
        long startTime = System.currentTimeMillis();

        return dataService.getUserData(request.getUserId())
            .thenCompose(userData -> {
                // Get risk assessment
                CompletableFuture<RiskAssessmentResult> riskAssessment = 
                    riskService.assessRisk(userData);
                
                // Get behavior analysis
                CompletableFuture<BehaviorAnalysisResult> behaviorAnalysis = 
                    behaviorService.analyzeBehavior(userData);
                
                return CompletableFuture.allOf(riskAssessment, behaviorAnalysis)
                    .thenApply(v -> {
                        // Generate base offers
                        List<BaseOffer> baseOffers = generateBaseOffers(
                            riskAssessment.join(),
                            request
                        );
                        
                        // Optimize offers
                        List<OptimizedOffer> optimizedOffers = optimizationService.optimizeOffers(
                            baseOffers,
                            riskAssessment.join(),
                            behaviorAnalysis.join()
                        ).join();
                        
                        // Rank offers
                        List<RankedOffer> rankedOffers = rankOffers(
                            optimizedOffers,
                            behaviorAnalysis.join()
                        );
                        
                        long generationTime = System.currentTimeMillis() - startTime;
                        return new LoanOfferResponse(
                            rankedOffers,
                            UUID.randomUUID().toString(),
                            generationTime
                        );
                    });
            });
    }

    /**
     * Generates base offers based on risk assessment and request parameters.
     *
     * @param riskAssessment The risk assessment result
     * @param request The loan offer request
     * @return List of base offers
     */
    private List<BaseOffer> generateBaseOffers(
            RiskAssessmentResult riskAssessment,
            LoanOfferRequest request) {
        List<BaseOffer> offers = new ArrayList<>();
        
        // Generate offers with different tenures
        List<Integer> tenures = List.of(3, 6, 9, 12, 18, 24, 36);
        
        for (Integer tenure : tenures) {
            if (isTenureValid(tenure, riskAssessment)) {
                offers.add(new BaseOfferImpl(
                    request.getRequestedAmount(),
                    tenure,
                    riskAssessment.getROIRange(),
                    calculateProcessingFee(request.getRequestedAmount(), tenure)
                ));
            }
        }
        
        return offers;
    }

    /**
     * Ranks optimized offers based on behavior analysis.
     *
     * @param offers The list of optimized offers
     * @param behaviorAnalysis The behavior analysis result
     * @return List of ranked offers
     */
    private List<RankedOffer> rankOffers(
            List<OptimizedOffer> offers,
            BehaviorAnalysisResult behaviorAnalysis) {
        return offers.stream()
            .map(offer -> new RankedOfferImpl(
                offer,
                calculateOfferScore(offer, behaviorAnalysis),
                -1 // Rank will be set after sorting
            ))
            .sorted((o1, o2) -> Double.compare(o2.getScore(), o1.getScore()))
            .map(offer -> new RankedOfferImpl(
                offer.getOffer(),
                offer.getScore(),
                offers.indexOf(offer) + 1
            ))
            .collect(Collectors.toList());
    }

    /**
     * Calculates the score for an offer based on various factors.
     *
     * @param offer The optimized offer
     * @param behaviorAnalysis The behavior analysis result
     * @return The calculated score
     */
    private double calculateOfferScore(
            OptimizedOffer offer,
            BehaviorAnalysisResult behaviorAnalysis) {
        double conversionWeight = 0.4;
        double riskWeight = 0.3;
        double valueWeight = 0.3;

        return (behaviorAnalysis.getConversionProbability() * conversionWeight) +
               ((1 - offer.getRiskImpact()) * riskWeight) +
               (behaviorAnalysis.getLongTermValue() * valueWeight);
    }

    /**
     * Checks if a tenure is valid based on risk assessment.
     *
     * @param tenure The tenure to check
     * @param riskAssessment The risk assessment result
     * @return true if the tenure is valid
     */
    private boolean isTenureValid(Integer tenure, RiskAssessmentResult riskAssessment) {
        // Implement tenure validation logic based on risk assessment
        return true; // Placeholder implementation
    }

    /**
     * Calculates the processing fee for a loan.
     *
     * @param amount The loan amount
     * @param tenure The loan tenure
     * @return The processing fee
     */
    private double calculateProcessingFee(Double amount, Integer tenure) {
        // Implement processing fee calculation logic
        return amount * 0.02; // Placeholder implementation
    }
}

/**
 * Implementation of the BaseOffer interface.
 */
class BaseOfferImpl implements BaseOffer {
    private final Double amount;
    private final Integer tenure;
    private final Double roi;
    private final Double processingFee;

    public BaseOfferImpl(Double amount, Integer tenure, Double roi, Double processingFee) {
        this.amount = amount;
        this.tenure = tenure;
        this.roi = roi;
        this.processingFee = processingFee;
    }

    @Override
    public Double getAmount() {
        return amount;
    }

    @Override
    public Integer getTenure() {
        return tenure;
    }

    @Override
    public Double getROI() {
        return roi;
    }

    @Override
    public Double getProcessingFee() {
        return processingFee;
    }
}

/**
 * Implementation of the RankedOffer interface.
 */
class RankedOfferImpl implements RankedOffer {
    private final OptimizedOffer offer;
    private final Double score;
    private final Integer rank;

    public RankedOfferImpl(OptimizedOffer offer, Double score, Integer rank) {
        this.offer = offer;
        this.score = score;
        this.rank = rank;
    }

    @Override
    public OptimizedOffer getOffer() {
        return offer;
    }

    @Override
    public Double getScore() {
        return score;
    }

    @Override
    public Integer getRank() {
        return rank;
    }
} 