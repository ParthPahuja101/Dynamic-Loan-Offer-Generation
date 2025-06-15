package com.cred.loan.monitoring.aspect;

import com.cred.loan.monitoring.service.LoggingService;
import com.cred.loan.monitoring.service.MetricsService;
import io.micrometer.core.instrument.Timer;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * Aspect for handling cross-cutting monitoring concerns.
 * Provides method execution timing, logging, and metrics collection.
 */
@Aspect
@Component
public class MonitoringAspect {
    private final LoggingService loggingService;
    private final MetricsService metricsService;

    public MonitoringAspect(LoggingService loggingService, MetricsService metricsService) {
        this.loggingService = loggingService;
        this.metricsService = metricsService;
    }

    /**
     * Around advice for monitoring method execution.
     * Records execution time, logs method entry/exit, and collects metrics.
     *
     * @param joinPoint The join point being advised
     * @return The result of the method execution
     * @throws Throwable If an error occurs during method execution
     */
    @Around("@annotation(org.springframework.web.bind.annotation.RequestMapping) || " +
            "@annotation(org.springframework.web.bind.annotation.GetMapping) || " +
            "@annotation(org.springframework.web.bind.annotation.PostMapping) || " +
            "@annotation(org.springframework.web.bind.annotation.PutMapping) || " +
            "@annotation(org.springframework.web.bind.annotation.DeleteMapping)")
    public Object monitorMethodExecution(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getTarget().getClass().getSimpleName();
        String correlationId = generateCorrelationId();

        try {
            // Log method entry
            loggingService.logWithCorrelationId(
                correlationId,
                LoggingService.LogLevel.INFO,
                "Entering method: {}.{} with args: {}",
                className,
                methodName,
                Arrays.toString(joinPoint.getArgs())
            );

            // Start timer
            Timer.Sample sample = Timer.start();
            Object result = joinPoint.proceed();
            Timer timer = metricsService.recordTimer("method.execution.time", 
                "class", className,
                "method", methodName);
            sample.stop(timer);

            // Log method exit
            loggingService.logWithCorrelationId(
                correlationId,
                LoggingService.LogLevel.INFO,
                "Exiting method: {}.{} with result: {}",
                className,
                methodName,
                result
            );

            return result;
        } catch (Throwable e) {
            // Log error
            loggingService.logWithCorrelationId(
                correlationId,
                LoggingService.LogLevel.ERROR,
                "Error in method: {}.{}",
                className,
                methodName,
                e
            );

            // Record error metric
            metricsService.incrementCounter("method.execution.error",
                "class", className,
                "method", methodName,
                "exception", e.getClass().getSimpleName());

            throw e;
        }
    }

    /**
     * Generates a unique correlation ID for request tracking.
     *
     * @return A unique correlation ID
     */
    private String generateCorrelationId() {
        return java.util.UUID.randomUUID().toString();
    }
} 