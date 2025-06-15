package com.cred.loan.data.service;

import java.time.Duration;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

/**
 * Service responsible for managing Redis-based caching operations.
 * Handles cache operations for user data, risk scores, behavior data, and offer data.
 */
public interface CacheManager {
    /**
     * Retrieves a value from the cache.
     *
     * @param key The cache key
     * @return CompletableFuture containing the cached value if present
     */
    CompletableFuture<Optional<Object>> get(String key);

    /**
     * Stores a value in the cache with an expiration time.
     *
     * @param key The cache key
     * @param value The value to cache
     * @param ttl The time-to-live duration
     * @return CompletableFuture that completes when the value is cached
     */
    CompletableFuture<Void> set(String key, Object value, Duration ttl);

    /**
     * Removes a value from the cache.
     *
     * @param key The cache key to remove
     * @return CompletableFuture that completes when the value is removed
     */
    CompletableFuture<Void> delete(String key);

    /**
     * Checks if a key exists in the cache.
     *
     * @param key The cache key to check
     * @return CompletableFuture containing true if the key exists
     */
    CompletableFuture<Boolean> exists(String key);

    /**
     * Sets an expiration time for an existing key.
     *
     * @param key The cache key
     * @param ttl The new time-to-live duration
     * @return CompletableFuture containing true if the expiration was set
     */
    CompletableFuture<Boolean> expire(String key, Duration ttl);

    /**
     * Increments a counter in the cache.
     *
     * @param key The counter key
     * @return CompletableFuture containing the new counter value
     */
    CompletableFuture<Long> increment(String key);

    /**
     * Decrements a counter in the cache.
     *
     * @param key The counter key
     * @return CompletableFuture containing the new counter value
     */
    CompletableFuture<Long> decrement(String key);
} 