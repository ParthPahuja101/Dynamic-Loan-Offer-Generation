package com.cred.loan.monitoring.config;

import io.micrometer.core.aop.TimedAspect;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for monitoring components.
 * Sets up Micrometer metrics and logging configurations.
 */
@Configuration
public class MonitoringConfig {
    /**
     * Creates a TimedAspect bean for method-level timing.
     *
     * @param registry The MeterRegistry to use
     * @return A configured TimedAspect instance
     */
    @Bean
    public TimedAspect timedAspect(MeterRegistry registry) {
        return new TimedAspect(registry);
    }
} 