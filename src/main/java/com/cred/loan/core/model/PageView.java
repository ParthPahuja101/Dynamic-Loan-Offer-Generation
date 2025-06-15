package com.cred.loan.core.model;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * Represents a page view event in the user's journey.
 */
public class PageView {
    private final String pageId;
    private final String actionType;
    private final Map<String, Object> actionData;
    private final LocalDateTime timestamp;

    public PageView(String pageId, String actionType, Map<String, Object> actionData, LocalDateTime timestamp) {
        this.pageId = pageId;
        this.actionType = actionType;
        this.actionData = actionData;
        this.timestamp = timestamp;
    }

    public String getPageId() {
        return pageId;
    }

    public String getActionType() {
        return actionType;
    }

    public Map<String, Object> getActionData() {
        return actionData;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
} 