package com.cred.loan.core.model.impl;

import com.cred.loan.core.model.UserData;

/**
 * Implementation of the UserData interface.
 */
public class UserDataImpl implements UserData {
    private final String userId;
    private final Double creditScore;
    private final Double income;
    private final Double existingDebt;
    private final Integer age;
    private final String employmentStatus;
    private final Integer employmentTenure;
    private final String city;
    private final String deviceType;

    public UserDataImpl(
            String userId,
            Double creditScore,
            Double income,
            Double existingDebt,
            Integer age,
            String employmentStatus,
            Integer employmentTenure,
            String city,
            String deviceType) {
        this.userId = userId;
        this.creditScore = creditScore;
        this.income = income;
        this.existingDebt = existingDebt;
        this.age = age;
        this.employmentStatus = employmentStatus;
        this.employmentTenure = employmentTenure;
        this.city = city;
        this.deviceType = deviceType;
    }

    @Override
    public String getUserId() {
        return userId;
    }

    @Override
    public Double getCreditScore() {
        return creditScore;
    }

    @Override
    public Double getIncome() {
        return income;
    }

    @Override
    public Double getExistingDebt() {
        return existingDebt;
    }

    @Override
    public Integer getAge() {
        return age;
    }

    @Override
    public String getEmploymentStatus() {
        return employmentStatus;
    }

    @Override
    public Integer getEmploymentTenure() {
        return employmentTenure;
    }

    @Override
    public String getCity() {
        return city;
    }

    @Override
    public String getDeviceType() {
        return deviceType;
    }
} 