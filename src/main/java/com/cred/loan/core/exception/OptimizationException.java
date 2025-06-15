package com.cred.loan.core.exception;

/**
 * Exception thrown when there is an error during offer optimization.
 * This exception is used to indicate problems in optimizing loan offers,
 * applying business rules, or other optimization-related operations.
 */
public class OptimizationException extends RuntimeException {
    /**
     * Creates a new optimization exception with a message.
     *
     * @param message The error message
     */
    public OptimizationException(String message) {
        super(message);
    }

    /**
     * Creates a new optimization exception with a message and cause.
     *
     * @param message The error message
     * @param cause The cause of the exception
     */
    public OptimizationException(String message, Throwable cause) {
        super(message, cause);
    }
} 