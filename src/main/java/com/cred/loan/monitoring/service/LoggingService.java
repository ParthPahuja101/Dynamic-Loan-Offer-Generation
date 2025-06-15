package com.cred.loan.monitoring.service;

/**
 * Service for managing application logging.
 * Provides methods for logging different levels of messages with proper context.
 */
public interface LoggingService {
    /**
     * Logs a debug message.
     *
     * @param message The message to log
     * @param args Optional arguments to format the message
     */
    void debug(String message, Object... args);

    /**
     * Logs an info message.
     *
     * @param message The message to log
     * @param args Optional arguments to format the message
     */
    void info(String message, Object... args);

    /**
     * Logs a warning message.
     *
     * @param message The message to log
     * @param args Optional arguments to format the message
     */
    void warn(String message, Object... args);

    /**
     * Logs an error message.
     *
     * @param message The message to log
     * @param args Optional arguments to format the message
     */
    void error(String message, Object... args);

    /**
     * Logs an error message with an exception.
     *
     * @param message The message to log
     * @param throwable The exception to log
     * @param args Optional arguments to format the message
     */
    void error(String message, Throwable throwable, Object... args);

    /**
     * Logs a message with a specific correlation ID.
     *
     * @param correlationId The correlation ID to associate with the log
     * @param level The log level
     * @param message The message to log
     * @param args Optional arguments to format the message
     */
    void logWithCorrelationId(String correlationId, LogLevel level, String message, Object... args);

    /**
     * Enum representing different log levels.
     */
    enum LogLevel {
        DEBUG,
        INFO,
        WARN,
        ERROR
    }
} 