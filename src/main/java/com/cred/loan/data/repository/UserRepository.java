package com.cred.loan.data.repository;

import com.cred.loan.core.model.UserData;
import java.util.Optional;

/**
 * Repository interface for user data persistence operations.
 */
public interface UserRepository {
    /**
     * Finds a user by their ID.
     *
     * @param userId the ID of the user to find
     * @return an Optional containing the user data if found, empty otherwise
     */
    Optional<UserData> findById(String userId);

    /**
     * Saves user data.
     *
     * @param userData the user data to save
     * @return the saved user data
     */
    UserData save(UserData userData);
} 