package com.cred.loan.data.service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

/**
 * Service responsible for database operations.
 * Provides methods for executing queries, updates, and managing transactions.
 */
public interface DatabaseClient {
    /**
     * Executes a query and returns the results.
     *
     * @param query The SQL query to execute
     * @param params The query parameters
     * @return CompletableFuture containing the query results
     */
    CompletableFuture<List<Map<String, Object>>> query(String query, Object[] params);

    /**
     * Executes an update operation (INSERT, UPDATE, DELETE).
     *
     * @param query The SQL update query
     * @param params The query parameters
     * @return CompletableFuture containing the number of affected rows
     */
    CompletableFuture<Integer> update(String query, Object[] params);

    /**
     * Executes a batch update operation.
     *
     * @param query The SQL update query
     * @param batchParams List of parameter arrays for batch processing
     * @return CompletableFuture containing the number of affected rows
     */
    CompletableFuture<int[]> batchUpdate(String query, List<Object[]> batchParams);

    /**
     * Executes a transaction.
     *
     * @param transaction The transaction to execute
     * @return CompletableFuture that completes when the transaction is done
     */
    CompletableFuture<Void> transaction(Supplier<CompletableFuture<Void>> transaction);

    /**
     * Executes a query and maps the results to a specific type.
     *
     * @param query The SQL query to execute
     * @param params The query parameters
     * @param rowMapper Function to map each row to the target type
     * @param <T> The type to map the results to
     * @return CompletableFuture containing the mapped results
     */
    <T> CompletableFuture<List<T>> queryForList(
            String query,
            Object[] params,
            java.util.function.Function<Map<String, Object>, T> rowMapper);

    /**
     * Executes a query and returns a single result.
     *
     * @param query The SQL query to execute
     * @param params The query parameters
     * @param rowMapper Function to map the row to the target type
     * @param <T> The type to map the result to
     * @return CompletableFuture containing the mapped result
     */
    <T> CompletableFuture<T> queryForObject(
            String query,
            Object[] params,
            java.util.function.Function<Map<String, Object>, T> rowMapper);
} 