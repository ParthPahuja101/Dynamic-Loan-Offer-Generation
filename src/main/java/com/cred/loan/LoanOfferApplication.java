package com.cred.loan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * Main application class for the Loan Offer Generation System.
 * This class bootstraps the Spring Boot application and enables necessary features
 * like caching and asynchronous processing.
 */
@SpringBootApplication
@EnableCaching
@EnableAsync
public class LoanOfferApplication {

    /**
     * Main method that starts the Spring Boot application.
     *
     * @param args Command line arguments passed to the application
     */
    public static void main(String[] args) {
        SpringApplication.run(LoanOfferApplication.class, args);
    }
} 