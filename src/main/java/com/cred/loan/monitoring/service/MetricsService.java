package com.cred.loan.monitoring.service;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Timer;

/**
 * Service for managing application metrics using Micrometer.
 * Provides methods for recording various metrics like counters, timers, and gauges.
 */
public interface MetricsService {
    /**
     * Records a counter increment for the specified metric.
     *
     * @param name The name of the metric
     * @param tags Optional tags to associate with the metric
     */
    void incrementCounter(String name, String... tags);

    /**
     * Records a counter decrement for the specified metric.
     *
     * @param name The name of the metric
     * @param tags Optional tags to associate with the metric
     */
    void decrementCounter(String name, String... tags);

    /**
     * Records a timer for the specified metric.
     *
     * @param name The name of the metric
     * @param tags Optional tags to associate with the metric
     * @return A Timer instance that can be used to record durations
     */
    Timer recordTimer(String name, String... tags);

    /**
     * Records a gauge value for the specified metric.
     *
     * @param name The name of the metric
     * @param value The value to record
     * @param tags Optional tags to associate with the metric
     */
    void recordGauge(String name, double value, String... tags);

    /**
     * Gets a counter for the specified metric.
     *
     * @param name The name of the metric
     * @param tags Optional tags to associate with the metric
     * @return A Counter instance
     */
    Counter getCounter(String name, String... tags);

    /**
     * Gets a timer for the specified metric.
     *
     * @param name The name of the metric
     * @param tags Optional tags to associate with the metric
     * @return A Timer instance
     */
    Timer getTimer(String name, String... tags);
} 