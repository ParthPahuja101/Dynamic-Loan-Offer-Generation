package com.cred.loan.core.service;

import com.cred.loan.core.model.LoanOfferRequest;
import com.cred.loan.core.model.LoanOfferResponse;

import java.util.concurrent.CompletableFuture;

/**
 * Service interface for generating loan offers.
 * This service orchestrates the entire loan offer generation process by coordinating
 * between risk assessment, behavior analysis, and optimization services.
 */
public interface OfferGenerationService {
    /**
     * Generates personalized loan offers based on the request.
     *
     * @param request The loan offer request containing user preferences
     * @return A CompletableFuture containing the loan offer response
     */
    CompletableFuture<LoanOfferResponse> generateOffers(LoanOfferRequest request);
} 