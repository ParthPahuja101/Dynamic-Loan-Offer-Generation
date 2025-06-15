package com.cred.loan.data.service.impl;

import com.cred.loan.data.service.CacheManager;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

/**
 * Implementation of the CacheManager interface using Redis.
 * Provides Redis-based caching operations with proper error handling and logging.
 */
@Service
public class CacheManagerImpl implements CacheManager {
    private final RedisTemplate<String, Object> redisTemplate;

    public CacheManagerImpl(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public CompletableFuture<Optional<Object>> get(String key) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                Object value = redisTemplate.opsForValue().get(key);
                return Optional.ofNullable(value);
            } catch (Exception e) {
                throw new RuntimeException("Failed to get value from cache: " + key, e);
            }
        });
    }

    @Override
    public CompletableFuture<Void> set(String key, Object value, Duration ttl) {
        return CompletableFuture.runAsync(() -> {
            try {
                redisTemplate.opsForValue().set(key, value, ttl);
            } catch (Exception e) {
                throw new RuntimeException("Failed to set value in cache: " + key, e);
            }
        });
    }

    @Override
    public CompletableFuture<Void> delete(String key) {
        return CompletableFuture.runAsync(() -> {
            try {
                redisTemplate.delete(key);
            } catch (Exception e) {
                throw new RuntimeException("Failed to delete value from cache: " + key, e);
            }
        });
    }

    @Override
    public CompletableFuture<Boolean> exists(String key) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                return Boolean.TRUE.equals(redisTemplate.hasKey(key));
            } catch (Exception e) {
                throw new RuntimeException("Failed to check key existence in cache: " + key, e);
            }
        });
    }

    @Override
    public CompletableFuture<Boolean> expire(String key, Duration ttl) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                return Boolean.TRUE.equals(redisTemplate.expire(key, ttl));
            } catch (Exception e) {
                throw new RuntimeException("Failed to set expiration for key: " + key, e);
            }
        });
    }

    @Override
    public CompletableFuture<Long> increment(String key) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                return redisTemplate.opsForValue().increment(key);
            } catch (Exception e) {
                throw new RuntimeException("Failed to increment counter: " + key, e);
            }
        });
    }

    @Override
    public CompletableFuture<Long> decrement(String key) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                return redisTemplate.opsForValue().decrement(key);
            } catch (Exception e) {
                throw new RuntimeException("Failed to decrement counter: " + key, e);
            }
        });
    }
} 