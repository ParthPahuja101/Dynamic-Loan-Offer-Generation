package com.cred.loan.data.repository.impl;

import com.cred.loan.core.model.UserData;
import com.cred.loan.data.repository.UserRepository;
import com.cred.loan.data.entity.UserEntity;
import com.cred.loan.data.mapper.UserMapper;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Implementation of UserRepository interface using an in-memory store.
 * In a production environment, this would be replaced with a proper database implementation.
 */
public class UserRepositoryImpl implements UserRepository {
    private final ConcurrentHashMap<String, UserEntity> userStore;
    private final UserMapper userMapper;

    public UserRepositoryImpl(UserMapper userMapper) {
        this.userStore = new ConcurrentHashMap<>();
        this.userMapper = userMapper;
    }

    @Override
    public Optional<UserData> findById(String userId) {
        UserEntity entity = userStore.get(userId);
        return Optional.ofNullable(entity)
                .map(userMapper::toModel);
    }

    @Override
    public UserData save(UserData userData) {
        UserEntity entity = userMapper.toEntity(userData);
        userStore.put(userData.getUserId(), entity);
        return userData;
    }
} 