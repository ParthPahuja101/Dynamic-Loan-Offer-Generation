package com.cred.loan.monitoring.service.impl;

import com.cred.loan.monitoring.service.MetricsService;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tag;
import io.micrometer.core.instrument.Timer;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation of the MetricsService interface using Micrometer.
 * Provides functionality for recording and managing application metrics.
 */
@Service
public class MetricsServiceImpl implements MetricsService {
    private final MeterRegistry meterRegistry;

    public MetricsServiceImpl(MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;
    }

    @Override
    public void incrementCounter(String name, String... tags) {
        getCounter(name, tags).increment();
    }

    @Override
    public void decrementCounter(String name, String... tags) {
        getCounter(name, tags).increment(-1.0);
    }

    @Override
    public Timer recordTimer(String name, String... tags) {
        return getTimer(name, tags);
    }

    @Override
    public void recordGauge(String name, double value, String... tags) {
        List<Tag> tagList = Arrays.stream(tags)
                .map(tag -> Tag.of(tag.split("=")[0], tag.split("=")[1]))
                .collect(Collectors.toList());
        meterRegistry.gauge(name, tagList, value);
    }

    @Override
    public Counter getCounter(String name, String... tags) {
        return Counter.builder(name)
                .tags(tags)
                .register(meterRegistry);
    }

    @Override
    public Timer getTimer(String name, String... tags) {
        return Timer.builder(name)
                .tags(tags)
                .register(meterRegistry);
    }
} 