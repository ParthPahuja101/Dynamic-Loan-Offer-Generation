package com.cred.loan.optimization.model;

/**
 * Enum representing different types of loan terms that can be adjusted
 * during the optimization process.
 */
public enum TermType {
    /**
     * Rate of Interest (ROI) for the loan.
     */
    ROI,

    /**
     * Processing fee charged for loan disbursement.
     */
    PROCESSING_FEE,

    /**
     * Loan tenure in months.
     */
    TENURE,

    /**
     * Total loan amount.
     */
    LOAN_AMOUNT
} 