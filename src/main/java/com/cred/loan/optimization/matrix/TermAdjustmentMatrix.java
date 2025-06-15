package com.cred.loan.optimization.matrix;

import com.cred.loan.optimization.model.TermAdjustment;
import com.cred.loan.optimization.model.TermConstraints;
import com.cred.loan.optimization.model.TermType;
import com.cred.loan.optimization.model.UserProfile;

/**
 * Interface for term-specific adjustment matrices.
 */
public interface TermAdjustmentMatrix {
    /**
     * Gets the adjustment for a term based on user profile.
     */
    TermAdjustment getAdjustment(TermType termType, UserProfile profile);

    /**
     * Validates if an adjustment is acceptable.
     */
    boolean validateAdjustment(TermAdjustment adjustment);

    /**
     * Gets the constraints for term adjustments.
     */
    TermConstraints getConstraints();
} 