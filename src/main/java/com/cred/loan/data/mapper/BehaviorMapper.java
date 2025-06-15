package com.cred.loan.data.mapper;

import com.cred.loan.core.model.BehaviorData;
import com.cred.loan.data.entity.BehaviorEntity;

/**
 * Mapper interface for converting between BehaviorData and BehaviorEntity.
 */
public interface BehaviorMapper {
    /**
     * Converts a BehaviorEntity to a BehaviorData model.
     *
     * @param entity the entity to convert
     * @return the converted model
     */
    BehaviorData toModel(BehaviorEntity entity);

    /**
     * Converts a BehaviorData model to a BehaviorEntity.
     *
     * @param model the model to convert
     * @return the converted entity
     */
    BehaviorEntity toEntity(BehaviorData model);
} 