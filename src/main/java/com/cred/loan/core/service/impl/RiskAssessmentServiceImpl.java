package com.cred.loan.core.service.impl;

import com.cred.loan.core.model.*;
import com.cred.loan.core.service.RiskAssessmentService;
import com.cred.loan.core.exception.RiskAssessmentException;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

/**
 * Implementation of the RiskAssessmentService interface.
 * This service is responsible for assessing user risk profiles and calculating
 * risk scores and ROI ranges based on user data.
 */
@Service
public class RiskAssessmentServiceImpl implements RiskAssessmentService {

    private static final double BASE_ROI = 12.0;
    private static final double MIN_ROI = 8.0;
    private static final double MAX_ROI = 24.0;
    private static final double RISK_RANGE = 0.25;

    @Override
    public CompletableFuture<RiskAssessmentResult> assessRisk(UserData userData) {
        return calculateRiskScore(userData)
            .thenCompose(riskScore -> calculateROIRange(riskScore)
                .thenApply(roiRange -> new RiskAssessmentResultImpl(
                    riskScore,
                    roiRange,
                    determineRiskLevel(riskScore),
                    identifyRiskFactors(userData),
                    generateRiskFactorExplanations(userData)
                )));
    }

    @Override
    public CompletableFuture<Double> calculateRiskScore(UserData userData) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                double creditScore = userData.getCreditScore() != null ? userData.getCreditScore() : 0.0;
                double income = userData.getIncome() != null ? userData.getIncome() : 0.0;
                double existingDebt = userData.getExistingDebt() != null ? userData.getExistingDebt() : 0.0;
                Integer age = userData.getAge();
                String employmentStatus = userData.getEmploymentStatus();
                Integer employmentTenure = userData.getEmploymentTenure();

                // Calculate component scores
                double creditScoreComponent = calculateCreditScoreComponent(creditScore);
                double incomeComponent = calculateIncomeComponent(income, existingDebt);
                double stabilityComponent = calculateStabilityComponent(age, employmentStatus, employmentTenure);

                // Weight the components
                double weightedScore = (creditScoreComponent * 0.4) +
                                     (incomeComponent * 0.4) +
                                     (stabilityComponent * 0.2);

                return Math.min(Math.max(weightedScore, 0.0), 1.0);
            } catch (Exception e) {
                throw new RiskAssessmentException("Error calculating risk score: " + e.getMessage(), e);
            }
        });
    }

    @Override
    public CompletableFuture<Double> calculateROIRange(Double riskScore) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                if (riskScore == null) {
                    throw new RiskAssessmentException("Risk score cannot be null");
                }

                // Calculate base ROI based on risk score
                double baseROI = BASE_ROI + (riskScore * (MAX_ROI - MIN_ROI));

                // Apply risk range
                double minROI = Math.max(baseROI - RISK_RANGE, MIN_ROI);
                double maxROI = Math.min(baseROI + RISK_RANGE, MAX_ROI);

                return (minROI + maxROI) / 2;
            } catch (Exception e) {
                throw new RiskAssessmentException("Error calculating ROI range: " + e.getMessage(), e);
            }
        });
    }

    /**
     * Calculates the credit score component of the risk assessment.
     *
     * @param creditScore The user's credit score
     * @return The credit score component (0.0 to 1.0)
     */
    private double calculateCreditScoreComponent(double creditScore) {
        // Normalize credit score to 0-1 range (assuming credit score is 300-850)
        return Math.min(Math.max((creditScore - 300) / 550, 0.0), 1.0);
    }

    /**
     * Calculates the income component of the risk assessment.
     *
     * @param income The user's income
     * @param existingDebt The user's existing debt
     * @return The income component (0.0 to 1.0)
     */
    private double calculateIncomeComponent(double income, double existingDebt) {
        if (income <= 0) {
            return 0.0;
        }

        // Calculate debt-to-income ratio
        double dti = existingDebt / income;
        
        // Convert DTI to a 0-1 score (lower DTI is better)
        return Math.max(0.0, 1.0 - (dti / 0.5));
    }

    /**
     * Calculates the stability component of the risk assessment.
     *
     * @param age The user's age
     * @param employmentStatus The user's employment status
     * @param employmentTenure The user's employment tenure in months
     * @return The stability component (0.0 to 1.0)
     */
    private double calculateStabilityComponent(
            Integer age,
            String employmentStatus,
            Integer employmentTenure) {
        double ageScore = calculateAgeScore(age);
        double employmentScore = calculateEmploymentScore(employmentStatus, employmentTenure);

        return (ageScore * 0.4) + (employmentScore * 0.6);
    }

    /**
     * Calculates the age score component.
     *
     * @param age The user's age
     * @return The age score (0.0 to 1.0)
     */
    private double calculateAgeScore(Integer age) {
        if (age == null || age < 18) {
            return 0.0;
        }
        if (age > 65) {
            return 0.5;
        }
        // Higher score for ages 30-50
        return Math.min(Math.max((age - 18) / 32.0, 0.0), 1.0);
    }

    /**
     * Calculates the employment score component.
     *
     * @param employmentStatus The user's employment status
     * @param employmentTenure The user's employment tenure in months
     * @return The employment score (0.0 to 1.0)
     */
    private double calculateEmploymentScore(String employmentStatus, Integer employmentTenure) {
        double statusScore = calculateEmploymentStatusScore(employmentStatus);
        double tenureScore = calculateEmploymentTenureScore(employmentTenure);

        return (statusScore * 0.6) + (tenureScore * 0.4);
    }

    /**
     * Calculates the employment status score.
     *
     * @param employmentStatus The user's employment status
     * @return The employment status score (0.0 to 1.0)
     */
    private double calculateEmploymentStatusScore(String employmentStatus) {
        if (employmentStatus == null) {
            return 0.0;
        }

        return switch (employmentStatus.toLowerCase()) {
            case "permanent" -> 1.0;
            case "contract" -> 0.8;
            case "self-employed" -> 0.7;
            case "part-time" -> 0.5;
            default -> 0.3;
        };
    }

    /**
     * Calculates the employment tenure score.
     *
     * @param employmentTenure The user's employment tenure in months
     * @return The employment tenure score (0.0 to 1.0)
     */
    private double calculateEmploymentTenureScore(Integer employmentTenure) {
        if (employmentTenure == null || employmentTenure < 0) {
            return 0.0;
        }
        // Cap the score at 5 years (60 months)
        return Math.min(employmentTenure / 60.0, 1.0);
    }

    /**
     * Determines the risk level based on the risk score.
     *
     * @param riskScore The calculated risk score
     * @return The risk level
     */
    private RiskLevel determineRiskLevel(Double riskScore) {
        if (riskScore == null) {
            return RiskLevel.HIGH;
        }

        if (riskScore >= 0.7) {
            return RiskLevel.LOW;
        } else if (riskScore >= 0.4) {
            return RiskLevel.MEDIUM;
        } else {
            return RiskLevel.HIGH;
        }
    }

    /**
     * Identifies risk factors based on user data.
     *
     * @param userData The user data
     * @return Set of identified risk factors
     */
    private Set<RiskFactor> identifyRiskFactors(UserData userData) {
        Set<RiskFactor> riskFactors = new HashSet<>();

        if (userData.getCreditScore() != null && userData.getCreditScore() < 650) {
            riskFactors.add(RiskFactor.CREDIT_SCORE);
        }

        if (userData.getIncome() != null && userData.getExistingDebt() != null) {
            double dti = userData.getExistingDebt() / userData.getIncome();
            if (dti > 0.4) {
                riskFactors.add(RiskFactor.FOIR);
            }
        }

        if (userData.getEmploymentTenure() != null && userData.getEmploymentTenure() < 6) {
            riskFactors.add(RiskFactor.INCOME_STABILITY);
        }

        return riskFactors;
    }

    /**
     * Generates explanations for identified risk factors.
     *
     * @param userData The user data
     * @return Map of risk factors to their explanations
     */
    private Map<RiskFactor, String> generateRiskFactorExplanations(UserData userData) {
        Map<RiskFactor, String> explanations = new HashMap<>();
        Set<RiskFactor> riskFactors = identifyRiskFactors(userData);

        for (RiskFactor factor : riskFactors) {
            explanations.put(factor, generateExplanation(factor, userData));
        }

        return explanations;
    }

    /**
     * Generates an explanation for a specific risk factor.
     *
     * @param factor The risk factor
     * @param userData The user data
     * @return The explanation string
     */
    private String generateExplanation(RiskFactor factor, UserData userData) {
        return switch (factor) {
            case CREDIT_SCORE -> String.format(
                "Credit score of %.0f is below the recommended threshold of 650",
                userData.getCreditScore()
            );
            case FOIR -> String.format(
                "Debt-to-income ratio of %.2f exceeds the recommended threshold of 0.4",
                userData.getExistingDebt() / userData.getIncome()
            );
            case INCOME_STABILITY -> String.format(
                "Employment tenure of %d months is below the recommended minimum of 6 months",
                userData.getEmploymentTenure()
            );
            default -> "Risk factor identified";
        };
    }
}

/**
 * Implementation of the RiskAssessmentResult interface.
 */
class RiskAssessmentResultImpl implements RiskAssessmentResult {
    private final Double riskScore;
    private final Double roiRange;
    private final RiskLevel riskLevel;
    private final Set<String> riskFactors;
    private final Map<String, String> riskFactorExplanations;

    public RiskAssessmentResultImpl(
            Double riskScore,
            Double roiRange,
            RiskLevel riskLevel,
            Set<RiskFactor> riskFactors,
            Map<RiskFactor, String> riskFactorExplanations) {
        this.riskScore = riskScore;
        this.roiRange = roiRange;
        this.riskLevel = riskLevel;
        this.riskFactors = riskFactors.stream()
            .map(RiskFactor::name)
            .collect(Collectors.toSet());
        this.riskFactorExplanations = riskFactorExplanations.entrySet().stream()
            .collect(Collectors.toMap(
                e -> e.getKey().name(),
                Map.Entry::getValue
            ));
    }

    @Override
    public Double getRiskScore() {
        return riskScore;
    }

    @Override
    public Double getROIRange() {
        return roiRange;
    }

    @Override
    public RiskLevel getRiskLevel() {
        return riskLevel;
    }

    @Override
    public Set<String> getRiskFactors() {
        return riskFactors;
    }

    @Override
    public Map<String, String> getRiskFactorExplanations() {
        return riskFactorExplanations;
    }
} 