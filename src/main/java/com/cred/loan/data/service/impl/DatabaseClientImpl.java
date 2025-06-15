package com.cred.loan.data.service.impl;

import com.cred.loan.data.service.DatabaseClient;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Implementation of the DatabaseClient interface using Spring JDBC.
 * Provides database operations with proper transaction management and error handling.
 */
@Service
public class DatabaseClientImpl implements DatabaseClient {
    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final TransactionTemplate transactionTemplate;

    public DatabaseClientImpl(
            JdbcTemplate jdbcTemplate,
            NamedParameterJdbcTemplate namedParameterJdbcTemplate,
            PlatformTransactionManager transactionManager) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.transactionTemplate = new TransactionTemplate(transactionManager);
    }

    @Override
    public CompletableFuture<List<Map<String, Object>>> query(String query, Object[] params) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                return jdbcTemplate.queryForList(query, params);
            } catch (Exception e) {
                throw new RuntimeException("Failed to execute query: " + query, e);
            }
        });
    }

    @Override
    public CompletableFuture<Integer> update(String query, Object[] params) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                return jdbcTemplate.update(query, params);
            } catch (Exception e) {
                throw new RuntimeException("Failed to execute update: " + query, e);
            }
        });
    }

    @Override
    public CompletableFuture<int[]> batchUpdate(String query, List<Object[]> batchParams) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                return jdbcTemplate.batchUpdate(query, batchParams);
            } catch (Exception e) {
                throw new RuntimeException("Failed to execute batch update: " + query, e);
            }
        });
    }

    @Override
    public CompletableFuture<Void> transaction(Supplier<CompletableFuture<Void>> transaction) {
        return CompletableFuture.runAsync(() -> {
            try {
                transactionTemplate.execute(new TransactionCallbackWithoutResult() {
                    @Override
                    protected void doInTransactionWithoutResult(TransactionStatus status) {
                        try {
                            transaction.get().join();
                        } catch (Exception e) {
                            status.setRollbackOnly();
                            throw new RuntimeException("Transaction failed", e);
                        }
                    }
                });
            } catch (Exception e) {
                throw new RuntimeException("Failed to execute transaction", e);
            }
        });
    }

    @Override
    public <T> CompletableFuture<List<T>> queryForList(
            String query,
            Object[] params,
            Function<Map<String, Object>, T> rowMapper) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                return jdbcTemplate.query(
                    query,
                    params,
                    (rs, rowNum) -> rowMapper.apply(convertResultSetToMap(rs))
                );
            } catch (Exception e) {
                throw new RuntimeException("Failed to execute query for list: " + query, e);
            }
        });
    }

    @Override
    public <T> CompletableFuture<T> queryForObject(
            String query,
            Object[] params,
            Function<Map<String, Object>, T> rowMapper) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                return jdbcTemplate.queryForObject(
                    query,
                    params,
                    (rs, rowNum) -> rowMapper.apply(convertResultSetToMap(rs))
                );
            } catch (Exception e) {
                throw new RuntimeException("Failed to execute query for object: " + query, e);
            }
        });
    }

    private Map<String, Object> convertResultSetToMap(java.sql.ResultSet rs) throws java.sql.SQLException {
        java.util.HashMap<String, Object> map = new java.util.HashMap<>();
        java.sql.ResultSetMetaData metaData = rs.getMetaData();
        int columnCount = metaData.getColumnCount();
        
        for (int i = 1; i <= columnCount; i++) {
            String columnName = metaData.getColumnName(i);
            Object value = rs.getObject(i);
            map.put(columnName, value);
        }
        
        return map;
    }
} 