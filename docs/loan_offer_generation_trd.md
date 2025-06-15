# Technical Requirements Document: Dynamic Loan Offer Generation System

## Table of Contents
1. [System Overview](#1-system-overview)
   - [Purpose](#11-purpose)
   - [System Goals](#12-system-goals)
2. [Technical Requirements](#2-technical-requirements)
   - [Input Parameters](#21-input-parameters)
   - [Offer Generation Components](#22-offer-generation-components)
   - [Business Rules](#23-business-rules)
3. [Technical Specifications](#3-technical-specifications)
   - [System Architecture](#31-system-architecture)
   - [Data Requirements](#32-data-requirements)
   - [Performance Requirements](#33-performance-requirements)
   - [Security Requirements](#34-security-requirements)
4. [Integration Requirements](#4-integration-requirements)
   - [External Systems](#41-external-systems)
   - [Internal Systems](#42-internal-systems)
5. [Monitoring and Analytics](#5-monitoring-and-analytics)
   - [Key Metrics](#51-key-metrics)
   - [Alerts and Notifications](#52-alerts-and-notifications)
6. [Implementation Phases](#6-implementation-phases)
7. [Success Criteria](#7-success-criteria)

## 1. System Overview

### 1.1 Purpose
The Dynamic Loan Offer Generation System is designed to create personalized loan offers for CRED users by intelligently balancing user preferences, risk parameters, and business objectives.

### 1.2 System Goals
- Generate optimal loan offers based on user risk profile and behavior
- Maximize conversion rates while maintaining profitability
- Ensure compliance with lender constraints and business rules
- Provide a scalable and maintainable solution

## 2. Technical Requirements

### 2.1 Input Parameters

#### 2.1.1 User Risk Parameters
- Credit Bureau Score
- FOIR (Fixed Obligation to Income Ratio)
- Credit Limit Utilization
- Repayment Frequency

#### 2.1.2 User Behavior Metrics
- Price Sensitivity Score
- Conversion Probability
- Long-term Value Score

#### 2.1.3 Lender Parameters
- Hurdle Rate
- Maximum Loan Limit
- Processing Fee Range
- Available Tenure Options
- Lender's Monthly/Quarterly Loan Capacity

### 2.2 Offer Generation Components

#### 2.2.1 Risk Assessment Module
- Input: User Risk Parameters
- Output: 
  - Ideal Terms
    - Loan Limit
    - ROI
    - Processing Fee
    - Tenure Options
  - Acceptable Range
    - Min/Max Loan Limit
    - Min/Max ROI
    - Min/Max Processing Fee
    - Available Tenure Options

#### 2.2.2 Offer Optimization Module
- Input: 
  - Risk Assessment Output
  - User Behavior Metrics
  - Lender Parameters
- Output: Optimized Loan Offer

### 2.3 Business Rules

#### 2.3.1 Risk-Based Rules
- Must not exceed maximum acceptable risk thresholds
- Must maintain minimum profitability margins
- Must comply with regulatory requirements

#### 2.3.2 Price Sensitivity Rules

##### 2.3.2.1 Base ROI Determination
- Base ROI is determined by risk assessment module
- Risk assessment considers:
  - Credit Bureau Score
  - FOIR
  - Credit Limit Utilization
  - Repayment Frequency
- Base ROI serves as the starting point for all offer calculations

##### 2.3.2.2 Loan Terms Adjustment Framework

1. Scope and Design Philosophy
   - Primary Focus: Dynamic ROI adjustment based on user behavior and risk profile
   - Other Terms (Limit, Processing Fee, Tenure): Use risk-determined ideal values
   - Design is extensible to include dynamic adjustment of other loan terms in future iterations
   - Current implementation serves as a proof of concept for behavior-based term optimization

2. Base Terms Determination (Risk Assessment)
   - ROI: Risk-determined ideal value and acceptable range
   - Other Terms: Use risk-determined ideal values directly
   - Risk assessment provides the foundation for all term calculations

3. ROI Value Limitations and Constraints

| Parameter | Value | Notes |
|-----------|-------|-------|
| Base ROI | Risk-determined | Starting point for all calculations |
| Min ROI | Hurdle Rate - 0.5% | Maximum loss from hurdle rate |
| Max ROI | Hurdle Rate + 4% | Maximum gain above hurdle rate |
| Risk Range | ±0.25% | Maximum deviation from risk-determined base |

Explanation:
This matrix defines the fundamental boundaries for ROI calculations. The Base ROI is determined by the risk assessment module and serves as the anchor point. The Min and Max ROI values establish the absolute limits within which any ROI adjustments must operate, ensuring we never go below the minimum profitable threshold or exceed maximum market-competitive rates. The Risk Range parameter ensures that any adjustments stay within a safe deviation from the risk-determined base value.

4. ROI Adjustment Matrix (Based on Risk-Approved ROI)

| User Profile | Low Price Sensitivity | Medium Price Sensitivity | High Price Sensitivity | Constraints |
|--------------|----------------------|--------------------------|------------------------|-------------|
| High Conv + High LTV | Base + 0.5% (max) | Base | Base - 0.25% (min) | LTV > 0.8, Conv > 0.7 |
| High Conv + Medium LTV | Base + 0.25% | Base | Base - 0.5% (min) | LTV > 0.6, Conv > 0.7 |
| High Conv + Low LTV | Base + 0.75% (max) | Base + 0.25% | Base - 0.25% (min) | Conv > 0.7 |
| Medium Conv + High LTV | Base | Base - 0.25% (min) | Base - 0.75% (min) | LTV > 0.8 |
| Medium Conv + Medium LTV | Base + 0.25% | Base - 0.25% (min) | Base - 1% (min) | LTV > 0.6 |
| Medium Conv + Low LTV | Base + 0.5% (max) | Base | Base - 0.5% (min) | Conv > 0.4 |
| Low Conv + High LTV | Base - 0.25% (min) | Base - 0.75% (min) | Base - 1.25% (min) | LTV > 0.8 |
| Low Conv + Medium LTV | Base | Base - 0.5% (min) | Base - 1.5% (min) | LTV > 0.6 |
| Low Conv + Low LTV | Base + 0.25% | Base - 0.25% (min) | Base - 1.75% (min) | Conv > 0.3 |

Explanation:
This matrix provides a comprehensive framework for adjusting ROI based on user behavior and risk profile. It considers three key factors:
1. Price Sensitivity: How users react to price changes
2. Conversion Probability: Likelihood of offer acceptance
3. LTV (Loan-to-Value): Risk exposure relative to loan amount

The adjustments are designed to:
- Maximize revenue from price-insensitive users
- Increase conversion rates for price-sensitive users
- Balance risk and reward based on LTV ratios
- Maintain profitability while staying competitive

5. Risk Impact and Thresholds for ROI Adjustments

| Adjustment Type | Risk Impact | Max Allowed Impact | Required Mitigation |
|-----------------|-------------|-------------------|---------------------|
| ROI Increase | +2% per 0.5% | 10% | Monitor conversion |
| ROI Decrease | -1% per 0.5% | 5% | Monitor LTV |

Explanation:
This matrix quantifies the relationship between ROI adjustments and their impact on risk metrics. It helps in:
- Understanding how each ROI change affects risk exposure
- Setting clear limits on maximum allowed risk impact
- Defining required monitoring and mitigation steps
- Maintaining a balance between profitability and risk management

6. Value Preservation Rules for ROI

| Action | Min LTV | Max LTV | Min Conv | Max Conv | Max Risk Impact |
|--------|---------|---------|----------|----------|-----------------|
| ROI Increase | 0.8 | 1.2 | 0.6 | 0.9 | +2% |
| ROI Decrease | 0.9 | 1.5 | 0.7 | 1.0 | -1% |

Explanation:
These rules ensure that ROI adjustments don't compromise the overall value of the loan portfolio. They:
- Define acceptable LTV ranges for different ROI actions
- Set conversion rate thresholds to maintain profitability
- Limit maximum risk impact for each type of adjustment
- Balance short-term gains with long-term portfolio health

7. ROI Optimization Constraints

| Constraint Type | Min Value | Max Value | Base Value | Adjustment Range |
|-----------------|-----------|-----------|------------|------------------|
| Risk Score | Base - 5% | Base + 10% | Risk-determined | ±5% |
| Conversion Rate | 40% | 100% | Risk-determined | ±10% |
| Long-term Value | 0.6 | 2.0 | Risk-determined | ±0.2 |
| Portfolio Risk | Base - 3% | Base + 3% | Risk-determined | ±2% |

Explanation:
This matrix defines the operational boundaries for the optimization process. It:
- Sets minimum and maximum thresholds for key metrics
- Establishes base values derived from risk assessment
- Defines acceptable adjustment ranges for each constraint
- Ensures optimization stays within safe operational limits

The constraints work together to:
- Maintain portfolio health
- Ensure sustainable growth
- Balance risk and reward
- Support data-driven decision making

#### 2.3.3 Comprehensive Offer Optimization Matrix

##### 2.3.3.1 Price Sensitivity Categories
- High: User is very sensitive to price changes
- Medium: User shows moderate price sensitivity
- Low: User is less sensitive to price changes

##### 2.3.3.2 Conversion Probability Categories
- High: > 70% likelihood of accepting the offer
- Medium: 40-70% likelihood of accepting the offer
- Low: < 40% likelihood of accepting the offer

##### 2.3.3.3 Long-term Value Categories
- High: Expected lifetime value > 2x average
- Medium: Expected lifetime value 1-2x average
- Low: Expected lifetime value < 1x average



##### 2.3.3.4 Dynamic Adjustment Factors

1. Market Conditions
   - Adjust ROI by ±0.25% based on current market rates


2. Seasonal Factors
   - Festive seasons: Reduce ROI by 0.25% for all categories
   - End of quarter: Increase ROI by 0.25% for low long-term value users

3. User Engagement
   - Active users: Additional 0.25% ROI reduction
   - Inactive users: Additional 0.25% ROI increase

#### 2.3.4 Lender Capacity Rules
- Track and maintain lender-wise loan disbursement limits
- Implement queue management for high-demand periods
- Ensure fair distribution of loans across lenders

### 2.4 Offer Ranking System
- Score each offer based on:
  - User conversion probability
  - Company profitability
  - Long-term value potential
  - Risk-adjusted return

## 3. Technical Specifications

### 3.1 System Architecture
- Microservices-based architecture
- RESTful APIs for integration
- Real-time offer generation capability
- Caching layer for performance optimization

### 3.2 Data Requirements
- Real-time access to credit bureau data
- User behavior data warehouse
- Lender parameter database
- Historical offer performance data

### 3.3 Performance Requirements
- Offer generation time: < 2 seconds
- System availability: 99.9%
- Concurrent user handling: 1000+ requests per second

### 3.4 Security Requirements
- Data encryption at rest and in transit
- Role-based access control
- Audit logging for all offer generations
- Compliance with financial data protection standards

## 4. Integration Requirements

### 4.1 External Systems
- Credit Bureau Integration
- Lender Management System
- User Behavior Analytics System
- Payment Gateway Integration

### 4.2 Internal Systems
- User Profile Management
- Loan Management System
- Risk Management System
- Analytics Dashboard

## 5. Monitoring and Analytics

### 5.1 Key Metrics
- Offer Acceptance Rate
- Profitability per Offer
- Risk-adjusted Return
- System Performance Metrics

### 5.2 Alerts and Notifications
- Lender capacity threshold alerts
- Risk threshold breaches
- System performance degradation
- Unusual offer patterns

## 6. Implementation Phases

### Phase 1: Core Risk Assessment
- Implement basic risk assessment
- Set up lender parameter management
- Establish basic offer generation rules

### Phase 2: Optimization Engine
- Implement price sensitivity logic
- Add conversion probability scoring
- Integrate long-term value calculations

### Phase 3: Advanced Features
- Implement offer ranking system
- Add advanced analytics
- Optimize performance and scalability

## 7. Success Criteria
- 20% improvement in offer acceptance rate
- 15% increase in profitability
- 99.9% system uptime
- < 1% error rate in offer generation
- Compliance with all regulatory requirements
