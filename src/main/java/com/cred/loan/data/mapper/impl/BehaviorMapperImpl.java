package com.cred.loan.data.mapper.impl;

import com.cred.loan.core.model.BehaviorData;
import com.cred.loan.core.model.PageView;
import com.cred.loan.core.model.LoanCalculator;
import com.cred.loan.core.model.OfferInteraction;
import com.cred.loan.core.model.impl.BehaviorDataImpl;
import com.cred.loan.data.entity.BehaviorEntity;
import com.cred.loan.data.mapper.BehaviorMapper;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

/**
 * Implementation of BehaviorMapper interface.
 */
public class BehaviorMapperImpl implements BehaviorMapper {
    @Override
    public BehaviorData toModel(BehaviorEntity entity) {
        if (entity == null) {
            return null;
        }

        return new BehaviorDataImpl(
                entity.getUserId(),
                entity.getPageViews().stream()
                        .map(this::toPageView)
                        .collect(Collectors.toList()),
                entity.getCalculatorUsage().stream()
                        .map(this::toLoanCalculator)
                        .collect(Collectors.toList()),
                entity.getOfferInteractions().stream()
                        .map(this::toOfferInteraction)
                        .collect(Collectors.toList()),
                entity.getLastActive()
        );
    }

    @Override
    public BehaviorEntity toEntity(BehaviorData model) {
        if (model == null) {
            return null;
        }

        BehaviorEntity entity = new BehaviorEntity();
        entity.setUserId(model.getUserId());
        entity.setPageViews(model.getPageViews().stream()
                .map(this::toPageViewEntity)
                .collect(Collectors.toList()));
        entity.setCalculatorUsage(model.getCalculatorUsage().stream()
                .map(this::toLoanCalculatorEntity)
                .collect(Collectors.toList()));
        entity.setOfferInteractions(model.getOfferInteractions().stream()
                .map(this::toOfferInteractionEntity)
                .collect(Collectors.toList()));
        entity.setLastActive(model.getLastActive());
        entity.setCreatedAt(LocalDateTime.now());
        entity.setUpdatedAt(LocalDateTime.now());

        return entity;
    }

    private PageView toPageView(com.cred.loan.data.entity.PageView entity) {
        return new PageView(
                entity.getPageId(),
                entity.getActionType(),
                entity.getActionData(),
                entity.getTimestamp()
        );
    }

    private com.cred.loan.data.entity.PageView toPageViewEntity(PageView model) {
        com.cred.loan.data.entity.PageView entity = new com.cred.loan.data.entity.PageView();
        entity.setPageId(model.getPageId());
        entity.setActionType(model.getActionType());
        entity.setActionData(model.getActionData());
        entity.setTimestamp(model.getTimestamp());
        return entity;
    }

    private LoanCalculator toLoanCalculator(com.cred.loan.data.entity.LoanCalculator entity) {
        return new LoanCalculator(
                entity.getAmount(),
                entity.getTenure(),
                entity.getRoi(),
                entity.getTimestamp()
        );
    }

    private com.cred.loan.data.entity.LoanCalculator toLoanCalculatorEntity(LoanCalculator model) {
        com.cred.loan.data.entity.LoanCalculator entity = new com.cred.loan.data.entity.LoanCalculator();
        entity.setAmount(model.getAmount());
        entity.setTenure(model.getTenure());
        entity.setRoi(model.getRoi());
        entity.setTimestamp(model.getTimestamp());
        return entity;
    }

    private OfferInteraction toOfferInteraction(com.cred.loan.data.entity.OfferInteraction entity) {
        return new OfferInteraction(
                entity.getOfferId(),
                entity.getInteractionType(),
                entity.getTimestamp()
        );
    }

    private com.cred.loan.data.entity.OfferInteraction toOfferInteractionEntity(OfferInteraction model) {
        com.cred.loan.data.entity.OfferInteraction entity = new com.cred.loan.data.entity.OfferInteraction();
        entity.setOfferId(model.getOfferId());
        entity.setInteractionType(model.getInteractionType());
        entity.setTimestamp(model.getTimestamp());
        return entity;
    }
} 