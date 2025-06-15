package com.cred.loan.core.exception;

/**
 * Exception thrown when there is an error during risk assessment.
 * This exception is used to indicate problems in calculating risk scores,
 * determining ROI ranges, or other risk-related operations.
 */
public class RiskAssessmentException extends RuntimeException {
    /**
     * Creates a new risk assessment exception with a message.
     *
     * @param message The error message
     */
    public RiskAssessmentException(String message) {
        super(message);
    }

    /**
     * Creates a new risk assessment exception with a message and cause.
     *
     * @param message The error message
     * @param cause The cause of the exception
     */
    public RiskAssessmentException(String message, Throwable cause) {
        super(message, cause);
    }
} 