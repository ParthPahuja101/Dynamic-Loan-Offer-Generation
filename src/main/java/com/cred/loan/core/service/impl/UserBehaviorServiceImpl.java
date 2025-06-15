package com.cred.loan.core.service.impl;

import com.cred.loan.core.model.*;
import com.cred.loan.core.service.UserBehaviorService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * Implementation of the UserBehaviorService interface.
 * This service analyzes user behavior patterns to optimize loan offers
 * and predict conversion probabilities.
 */
@Service
public class UserBehaviorServiceImpl implements UserBehaviorService {

    private static final double BASE_CONVERSION_PROBABILITY = 0.5;
    private static final double MAX_CONVERSION_PROBABILITY = 0.95;
    private static final double MIN_CONVERSION_PROBABILITY = 0.1;

    @Override
    public CompletableFuture<BehaviorAnalysisResult> analyzeBehavior(UserData userData) {
        return CompletableFuture.supplyAsync(() -> {
            double priceSensitivity = calculatePriceSensitivity(userData).join();
            double conversionProbability = calculateConversionProbability(userData).join();
            double longTermValue = calculateLongTermValue(userData);

            return new BehaviorAnalysisResultImpl(
                new PriceSensitivityImpl(priceSensitivity, calculateConfidence(userData), LocalDateTime.now()),
                conversionProbability,
                longTermValue,
                LocalDateTime.now()
            );
        });
    }

    @Override
    public CompletableFuture<Double> calculatePriceSensitivity(UserData userData) {
        return CompletableFuture.supplyAsync(() -> {
            // Calculate price sensitivity based on user's behavior data
            double sensitivity = 0.0;
            int factors = 0;

            // Factor 1: Device Type
            if (userData.getDeviceType() != null) {
                sensitivity += calculateDeviceTypeSensitivity(userData.getDeviceType());
                factors++;
            }

            // Factor 2: City
            if (userData.getCity() != null) {
                sensitivity += calculateCitySensitivity(userData.getCity());
                factors++;
            }

            // Factor 3: Age
            if (userData.getAge() != null) {
                sensitivity += calculateAgeSensitivity(userData.getAge());
                factors++;
            }

            // Calculate average sensitivity
            return factors > 0 ? sensitivity / factors : 0.5;
        });
    }

    @Override
    public CompletableFuture<Double> calculateConversionProbability(UserData userData) {
        return CompletableFuture.supplyAsync(() -> {
            double baseProbability = BASE_CONVERSION_PROBABILITY;
            double adjustment = 0.0;

            // Adjust based on price sensitivity
            double priceSensitivity = calculatePriceSensitivity(userData).join();
            adjustment += (1 - priceSensitivity) * 0.2;

            // Adjust based on city
            if (userData.getCity() != null) {
                adjustment += calculateCityConversionAdjustment(userData.getCity());
            }

            // Adjust based on device type
            if (userData.getDeviceType() != null) {
                adjustment += calculateDeviceTypeConversionAdjustment(userData.getDeviceType());
            }

            // Calculate final probability
            double probability = baseProbability + adjustment;

            // Ensure probability is within bounds
            return Math.min(Math.max(probability, MIN_CONVERSION_PROBABILITY), MAX_CONVERSION_PROBABILITY);
        });
    }

    /**
     * Calculates the long-term value of a user.
     *
     * @param userData The user data
     * @return The long-term value score (0.0 to 1.0)
     */
    private double calculateLongTermValue(UserData userData) {
        double value = 0.0;
        int factors = 0;

        // Factor 1: Income
        if (userData.getIncome() != null) {
            value += calculateIncomeValue(userData.getIncome());
            factors++;
        }

        // Factor 2: Employment Status
        if (userData.getEmploymentStatus() != null) {
            value += calculateEmploymentValue(userData.getEmploymentStatus());
            factors++;
        }

        // Factor 3: Age
        if (userData.getAge() != null) {
            value += calculateAgeValue(userData.getAge());
            factors++;
        }

        return factors > 0 ? value / factors : 0.5;
    }

    /**
     * Calculates the confidence level of the behavior analysis.
     *
     * @param userData The user data
     * @return The confidence score (0.0 to 1.0)
     */
    private double calculateConfidence(UserData userData) {
        int dataPoints = 0;
        int totalPoints = 3; // Total number of data points we check

        if (userData.getDeviceType() != null) {
            dataPoints++;
        }
        if (userData.getCity() != null) {
            dataPoints++;
        }
        if (userData.getAge() != null) {
            dataPoints++;
        }

        return (double) dataPoints / totalPoints;
    }

    /**
     * Calculates price sensitivity based on device type.
     *
     * @param deviceType The user's device type
     * @return The device type sensitivity score (0.0 to 1.0)
     */
    private double calculateDeviceTypeSensitivity(String deviceType) {
        return switch (deviceType.toLowerCase()) {
            case "ios" -> 0.3; // iOS users tend to be less price sensitive
            case "android" -> 0.5; // Android users show moderate price sensitivity
            default -> 0.4; // Default to moderate sensitivity
        };
    }

    /**
     * Calculates price sensitivity based on city.
     *
     * @param city The user's city
     * @return The city sensitivity score (0.0 to 1.0)
     */
    private double calculateCitySensitivity(String city) {
        // Tier 1 cities tend to be less price sensitive
        List<String> tier1Cities = List.of("mumbai", "delhi", "bangalore", "hyderabad", "chennai");
        // Tier 2 cities show moderate price sensitivity
        List<String> tier2Cities = List.of("pune", "ahmedabad", "kolkata", "jaipur", "lucknow");

        String cityLower = city.toLowerCase();
        if (tier1Cities.contains(cityLower)) {
            return 0.3;
        } else if (tier2Cities.contains(cityLower)) {
            return 0.5;
        } else {
            return 0.7; // Other cities tend to be more price sensitive
        }
    }

    /**
     * Calculates price sensitivity based on age.
     *
     * @param age The user's age
     * @return The age sensitivity score (0.0 to 1.0)
     */
    private double calculateAgeSensitivity(Integer age) {
        if (age == null) {
            return 0.5;
        }

        if (age < 25) {
            return 0.7; // Younger users tend to be more price sensitive
        } else if (age < 35) {
            return 0.5; // Middle-aged users show moderate sensitivity
        } else if (age < 50) {
            return 0.4; // Older users tend to be less price sensitive
        } else {
            return 0.3; // Senior users are typically less price sensitive
        }
    }

    /**
     * Calculates conversion probability adjustment based on city.
     *
     * @param city The user's city
     * @return The city conversion adjustment (-0.1 to 0.1)
     */
    private double calculateCityConversionAdjustment(String city) {
        // Tier 1 cities have higher conversion rates
        List<String> tier1Cities = List.of("mumbai", "delhi", "bangalore", "hyderabad", "chennai");
        // Tier 2 cities have moderate conversion rates
        List<String> tier2Cities = List.of("pune", "ahmedabad", "kolkata", "jaipur", "lucknow");

        String cityLower = city.toLowerCase();
        if (tier1Cities.contains(cityLower)) {
            return 0.1;
        } else if (tier2Cities.contains(cityLower)) {
            return 0.05;
        } else {
            return -0.05; // Other cities have lower conversion rates
        }
    }

    /**
     * Calculates conversion probability adjustment based on device type.
     *
     * @param deviceType The user's device type
     * @return The device type conversion adjustment (-0.1 to 0.1)
     */
    private double calculateDeviceTypeConversionAdjustment(String deviceType) {
        return switch (deviceType.toLowerCase()) {
            case "ios" -> 0.1; // iOS users have higher conversion rates
            case "android" -> 0.05; // Android users have moderate conversion rates
            default -> 0.0; // Default to no adjustment
        };
    }

    /**
     * Calculates long-term value based on income.
     *
     * @param income The user's income
     * @return The income value score (0.0 to 1.0)
     */
    private double calculateIncomeValue(Double income) {
        if (income == null || income <= 0) {
            return 0.0;
        }

        // Normalize income to 0-1 range (assuming max income is 50L)
        return Math.min(income / 5000000.0, 1.0);
    }

    /**
     * Calculates long-term value based on employment status.
     *
     * @param employmentStatus The user's employment status
     * @return The employment value score (0.0 to 1.0)
     */
    private double calculateEmploymentValue(String employmentStatus) {
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
     * Calculates long-term value based on age.
     *
     * @param age The user's age
     * @return The age value score (0.0 to 1.0)
     */
    private double calculateAgeValue(Integer age) {
        if (age == null) {
            return 0.0;
        }

        if (age < 25) {
            return 0.6; // Younger users have moderate long-term value
        } else if (age < 35) {
            return 0.8; // Middle-aged users have high long-term value
        } else if (age < 50) {
            return 1.0; // Older users have highest long-term value
        } else {
            return 0.7; // Senior users have good long-term value
        }
    }
}

/**
 * Implementation of the BehaviorAnalysisResult interface.
 */
class BehaviorAnalysisResultImpl implements BehaviorAnalysisResult {
    private final PriceSensitivity priceSensitivity;
    private final Double conversionProbability;
    private final Double longTermValue;
    private final LocalDateTime analyzedAt;

    public BehaviorAnalysisResultImpl(
            PriceSensitivity priceSensitivity,
            Double conversionProbability,
            Double longTermValue,
            LocalDateTime analyzedAt) {
        this.priceSensitivity = priceSensitivity;
        this.conversionProbability = conversionProbability;
        this.longTermValue = longTermValue;
        this.analyzedAt = analyzedAt;
    }

    @Override
    public PriceSensitivity getPriceSensitivity() {
        return priceSensitivity;
    }

    @Override
    public Double getConversionProbability() {
        return conversionProbability;
    }

    @Override
    public Double getLongTermValue() {
        return longTermValue;
    }

    @Override
    public LocalDateTime getAnalyzedAt() {
        return analyzedAt;
    }
}

/**
 * Implementation of the PriceSensitivity interface.
 */
class PriceSensitivityImpl implements PriceSensitivity {
    private final Double sensitivity;
    private final Double confidence;
    private final LocalDateTime calculatedAt;

    public PriceSensitivityImpl(Double sensitivity, Double confidence, LocalDateTime calculatedAt) {
        this.sensitivity = sensitivity;
        this.confidence = confidence;
        this.calculatedAt = calculatedAt;
    }

    @Override
    public Double getSensitivity() {
        return sensitivity;
    }

    @Override
    public Double getConfidence() {
        return confidence;
    }

    @Override
    public LocalDateTime getCalculatedAt() {
        return calculatedAt;
    }
} 