package com.cred.loan.data.mapper.impl;

import com.cred.loan.core.model.RiskData;
import com.cred.loan.core.model.impl.RiskDataImpl;
import com.cred.loan.data.entity.RiskEntity;
import com.cred.loan.data.mapper.RiskMapper;

import java.time.LocalDateTime;

/**
 * Implementation of RiskMapper interface.
 */
public class RiskMapperImpl implements RiskMapper {
    @Override
    public RiskData toModel(RiskEntity entity) {
        if (entity == null) {
            return null;
        }

        return new RiskDataImpl(
                entity.getUserId(),
                entity.getRiskScore(),
                entity.getRiskLevel(),
                entity.getRiskFactors(),
                entity.getLastUpdated()
        );
    }

    @Override
    public RiskEntity toEntity(RiskData model) {
        if (model == null) {
            return null;
        }

        RiskEntity entity = new RiskEntity();
        entity.setUserId(model.getUserId());
        entity.setRiskScore(model.getRiskScore());
        entity.setRiskLevel(model.getRiskLevel());
        entity.setRiskFactors(model.getRiskFactors());
        entity.setLastUpdated(model.getLastUpdated());
        entity.setCreatedAt(LocalDateTime.now());
        entity.setUpdatedAt(LocalDateTime.now());

        return entity;
    }
} 