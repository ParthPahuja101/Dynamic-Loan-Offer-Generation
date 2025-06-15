package com.cred.loan.core.service;

import com.cred.loan.core.model.UserData;
import com.cred.loan.core.model.RiskAssessmentResult;

import java.util.concurrent.CompletableFuture;

/**
 * Service interface for assessing user risk profiles.
 * This service evaluates user data to determine risk scores and appropriate
 * risk parameters for loan offers.
 */
public interface RiskAssessmentService {
    /**
     * Assesses the risk profile of a user.
     *
     * @param userData The user data to assess
     * @return A CompletableFuture containing the risk assessment result
     */
    CompletableFuture<RiskAssessmentResult> assessRisk(UserData userData);

    /**
     * Calculates the risk score for a user.
     *
     * @param userData The user data to calculate risk score for
     * @return A CompletableFuture containing the risk score
     */
    CompletableFuture<Double> calculateRiskScore(UserData userData);

    /**
     * Calculates the ROI range based on risk score.
     *
     * @param riskScore The risk score to calculate ROI range for
     * @return A CompletableFuture containing the ROI range
     */
    CompletableFuture<Double> calculateROIRange(Double riskScore);
} 