package com.cred.loan.risk.model;

import com.cred.loan.core.model.RiskLevel;
import java.util.Map;
import java.util.Set;

public interface RiskAssessmentResult {
    Double getRiskScore();
    Double getROIRange();
    RiskLevel getRiskLevel();
    Set<String> getRiskFactors();
    Map<String, String> getRiskFactorExplanations();
} 