package com.cred.loan.data.entity;

import java.time.LocalDateTime;

/**
 * Entity class representing a user in the database.
 */
public class UserEntity {
    private String userId;
    private Double creditScore;
    private Double income;
    private Double existingDebt;
    private Integer age;
    private String employmentStatus;
    private Integer employmentTenure;
    private String city;
    private String deviceType;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Getters and Setters
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Double getCreditScore() {
        return creditScore;
    }

    public void setCreditScore(Double creditScore) {
        this.creditScore = creditScore;
    }

    public Double getIncome() {
        return income;
    }

    public void setIncome(Double income) {
        this.income = income;
    }

    public Double getExistingDebt() {
        return existingDebt;
    }

    public void setExistingDebt(Double existingDebt) {
        this.existingDebt = existingDebt;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getEmploymentStatus() {
        return employmentStatus;
    }

    public void setEmploymentStatus(String employmentStatus) {
        this.employmentStatus = employmentStatus;
    }

    public Integer getEmploymentTenure() {
        return employmentTenure;
    }

    public void setEmploymentTenure(Integer employmentTenure) {
        this.employmentTenure = employmentTenure;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
} 