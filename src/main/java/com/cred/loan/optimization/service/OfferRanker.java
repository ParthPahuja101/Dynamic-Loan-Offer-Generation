package com.cred.loan.optimization.service;

import com.cred.loan.core.model.OptimizedOffer;
import com.cred.loan.core.model.RankedOffer;
import com.cred.loan.core.model.impl.RankedOfferImpl;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Service responsible for ranking optimized loan offers based on various factors
 * including conversion probability, risk impact, and business value.
 */
public class OfferRanker {
    private final double conversionWeight;
    private final double riskWeight;
    private final double valueWeight;

    /**
     * Creates a new OfferRanker instance.
     *
     * @param conversionWeight Weight for conversion probability (0.0 to 1.0)
     * @param riskWeight Weight for risk impact (0.0 to 1.0)
     * @param valueWeight Weight for business value (0.0 to 1.0)
     * @throws IllegalArgumentException if weights don't sum to 1.0
     */
    public OfferRanker(double conversionWeight, double riskWeight, double valueWeight) {
        validateWeights(conversionWeight, riskWeight, valueWeight);
        this.conversionWeight = conversionWeight;
        this.riskWeight = riskWeight;
        this.valueWeight = valueWeight;
    }

    /**
     * Ranks a list of optimized offers based on their scores.
     *
     * @param offers List of optimized offers to rank
     * @return CompletableFuture containing the list of ranked offers
     */
    public CompletableFuture<List<RankedOffer>> rankOffers(List<OptimizedOffer> offers) {
        return CompletableFuture.supplyAsync(() -> {
            List<RankedOffer> rankedOffers = offers.stream()
                .map(offer -> new RankedOfferImpl(offer, calculateScore(offer), null))
                .sorted((a, b) -> Double.compare(b.getScore(), a.getScore()))
                .collect(Collectors.toList());

            // Assign ranks
            IntStream.range(0, rankedOffers.size())
                .forEach(i -> rankedOffers.set(i, new RankedOfferImpl(
                    rankedOffers.get(i).getOffer(),
                    rankedOffers.get(i).getScore(),
                    i + 1
                )));

            return rankedOffers;
        });
    }

    /**
     * Calculates the overall score for an offer.
     *
     * @param offer Optimized offer to score
     * @return Calculated score
     */
    private double calculateScore(OptimizedOffer offer) {
        double conversionScore = calculateConversionScore(offer);
        double riskScore = calculateRiskScore(offer);
        double valueScore = calculateValueScore(offer);

        return (conversionScore * conversionWeight) +
               (riskScore * riskWeight) +
               (valueScore * valueWeight);
    }

    /**
     * Calculates the conversion score component.
     *
     * @param offer Optimized offer
     * @return Conversion score (0.0 to 1.0)
     */
    private double calculateConversionScore(OptimizedOffer offer) {
        return offer.getConversionProbability();
    }

    /**
     * Calculates the risk score component.
     *
     * @param offer Optimized offer
     * @return Risk score (0.0 to 1.0)
     */
    private double calculateRiskScore(OptimizedOffer offer) {
        // Convert risk impact to a score (higher impact = lower score)
        return Math.max(0.0, 1.0 - offer.getRiskImpact());
    }

    /**
     * Calculates the business value score component.
     *
     * @param offer Optimized offer
     * @return Value score (0.0 to 1.0)
     */
    private double calculateValueScore(OptimizedOffer offer) {
        // Calculate value based on ROI and amount
        double roiValue = offer.getBaseOffer().getROI() / 100.0; // Normalize ROI
        double amountValue = Math.min(1.0, offer.getBaseOffer().getAmount() / 1000000.0); // Normalize amount

        return (roiValue * 0.7) + (amountValue * 0.3);
    }

    /**
     * Validates that the weights sum to 1.0.
     *
     * @param conversionWeight Weight for conversion probability
     * @param riskWeight Weight for risk impact
     * @param valueWeight Weight for business value
     * @throws IllegalArgumentException if weights don't sum to 1.0
     */
    private void validateWeights(double conversionWeight, double riskWeight, double valueWeight) {
        double sum = conversionWeight + riskWeight + valueWeight;
        if (Math.abs(sum - 1.0) > 0.0001) {
            throw new IllegalArgumentException(
                "Weights must sum to 1.0, got: " + sum
            );
        }
    }
} 