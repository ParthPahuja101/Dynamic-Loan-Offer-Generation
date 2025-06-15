package com.cred.loan.config;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.atomic.AtomicReference;

/**
 * Configuration class for application metrics using Micrometer.
 * This class sets up various metrics collectors for monitoring the loan offer generation system.
 */
@Configuration
public class MetricsConfig {

    private final AtomicReference<Double> averageRiskScore = new AtomicReference<>(0.0);

    /**
     * Creates a counter for tracking the number of generated loan offers.
     *
     * @param registry The meter registry
     * @return Configured counter
     */
    @Bean
    public Counter offerCounter(MeterRegistry registry) {
        return Counter.builder("loan.offers.generated")
            .description("Number of loan offers generated")
            .register(registry);
    }

    /**
     * Creates a timer for measuring offer generation duration.
     *
     * @param registry The meter registry
     * @return Configured timer
     */
    @Bean
    public Timer offerTimer(MeterRegistry registry) {
        return Timer.builder("loan.offers.generation.time")
            .description("Time taken to generate loan offers")
            .register(registry);
    }

    /**
     * Creates a gauge for monitoring average risk scores.
     *
     * @param registry The meter registry
     * @return Configured gauge
     */
    @Bean
    public Gauge riskGauge(MeterRegistry registry) {
        return Gauge.builder("loan.risk.score", averageRiskScore, AtomicReference::get)
            .description("Average risk score of generated offers")
            .register(registry);
    }

    /**
     * Creates a counter for tracking failed offer generations.
     *
     * @param registry The meter registry
     * @return Configured counter
     */
    @Bean
    public Counter failedOfferCounter(MeterRegistry registry) {
        return Counter.builder("loan.offers.failed")
            .description("Number of failed loan offer generations")
            .register(registry);
    }

    /**
     * Creates a counter for tracking successful offer acceptances.
     *
     * @param registry The meter registry
     * @return Configured counter
     */
    @Bean
    public Counter offerAcceptanceCounter(MeterRegistry registry) {
        return Counter.builder("loan.offers.accepted")
            .description("Number of accepted loan offers")
            .register(registry);
    }

    /**
     * Updates the average risk score.
     *
     * @param newScore The new risk score to incorporate
     */
    public void updateAverageRiskScore(double newScore) {
        averageRiskScore.set(newScore);
    }
} 