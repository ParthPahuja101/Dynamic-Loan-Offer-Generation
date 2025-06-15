package com.cred.loan.data.mapper.impl;

import com.cred.loan.core.model.UserData;
import com.cred.loan.core.model.impl.UserDataImpl;
import com.cred.loan.data.entity.UserEntity;
import com.cred.loan.data.mapper.UserMapper;

import java.time.LocalDateTime;

/**
 * Implementation of UserMapper interface.
 */
public class UserMapperImpl implements UserMapper {
    @Override
    public UserData toModel(UserEntity entity) {
        if (entity == null) {
            return null;
        }

        return new UserDataImpl(
                entity.getUserId(),
                entity.getCreditScore(),
                entity.getIncome(),
                entity.getExistingDebt(),
                entity.getAge(),
                entity.getEmploymentStatus(),
                entity.getEmploymentTenure(),
                entity.getCity(),
                entity.getDeviceType()
        );
    }

    @Override
    public UserEntity toEntity(UserData model) {
        if (model == null) {
            return null;
        }

        UserEntity entity = new UserEntity();
        entity.setUserId(model.getUserId());
        entity.setCreditScore(model.getCreditScore());
        entity.setIncome(model.getIncome());
        entity.setExistingDebt(model.getExistingDebt());
        entity.setAge(model.getAge());
        entity.setEmploymentStatus(model.getEmploymentStatus());
        entity.setEmploymentTenure(model.getEmploymentTenure());
        entity.setCity(model.getCity());
        entity.setDeviceType(model.getDeviceType());
        entity.setCreatedAt(LocalDateTime.now());
        entity.setUpdatedAt(LocalDateTime.now());

        return entity;
    }
} 