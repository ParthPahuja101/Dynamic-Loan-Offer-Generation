package com.cred.loan.monitoring.service.impl;

import com.cred.loan.monitoring.service.LoggingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.stereotype.Service;

/**
 * Implementation of the LoggingService interface using SLF4J.
 * Provides functionality for logging messages with proper context and correlation IDs.
 */
@Service
public class LoggingServiceImpl implements LoggingService {
    private static final String CORRELATION_ID_KEY = "correlationId";
    private final Logger logger = LoggerFactory.getLogger(LoggingServiceImpl.class);

    @Override
    public void debug(String message, Object... args) {
        logger.debug(message, args);
    }

    @Override
    public void info(String message, Object... args) {
        logger.info(message, args);
    }

    @Override
    public void warn(String message, Object... args) {
        logger.warn(message, args);
    }

    @Override
    public void error(String message, Object... args) {
        logger.error(message, args);
    }

    @Override
    public void error(String message, Throwable throwable, Object... args) {
        logger.error(message, args, throwable);
    }

    @Override
    public void logWithCorrelationId(String correlationId, LogLevel level, String message, Object... args) {
        try {
            MDC.put(CORRELATION_ID_KEY, correlationId);
            switch (level) {
                case DEBUG:
                    debug(message, args);
                    break;
                case INFO:
                    info(message, args);
                    break;
                case WARN:
                    warn(message, args);
                    break;
                case ERROR:
                    error(message, args);
                    break;
                default:
                    info(message, args);
            }
        } finally {
            MDC.remove(CORRELATION_ID_KEY);
        }
    }
} 