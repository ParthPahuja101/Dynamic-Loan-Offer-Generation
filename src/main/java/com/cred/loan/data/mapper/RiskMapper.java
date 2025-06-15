package com.cred.loan.data.mapper;

import com.cred.loan.core.model.RiskData;
import com.cred.loan.data.entity.RiskEntity;

/**
 * Mapper interface for converting between RiskData and RiskEntity.
 */
public interface RiskMapper {
    /**
     * Converts a RiskEntity to a RiskData model.
     *
     * @param entity the entity to convert
     * @return the converted model
     */
    RiskData toModel(RiskEntity entity);

    /**
     * Converts a RiskData model to a RiskEntity.
     *
     * @param model the model to convert
     * @return the converted entity
     */
    RiskEntity toEntity(RiskData model);
} 