package com.cred.loan.data.mapper;

import com.cred.loan.core.model.UserData;
import com.cred.loan.data.entity.UserEntity;

/**
 * Mapper interface for converting between UserData and UserEntity.
 */
public interface UserMapper {
    /**
     * Converts a UserEntity to a UserData model.
     *
     * @param entity the entity to convert
     * @return the converted model
     */
    UserData toModel(UserEntity entity);

    /**
     * Converts a UserData model to a UserEntity.
     *
     * @param model the model to convert
     * @return the converted entity
     */
    UserEntity toEntity(UserData model);
} 