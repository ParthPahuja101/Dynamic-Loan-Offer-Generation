package com.cred.loan.core.service;

import com.cred.loan.core.model.UserData;
import java.util.concurrent.CompletableFuture;

public interface DataService {
    CompletableFuture<UserData> getUserData(String userId);
} 