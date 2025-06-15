package com.cred.loan.data.entity;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * Entity class representing a page view event.
 */
public class PageView {
    private String pageId;
    private String actionType;
    private Map<String, Object> actionData;
    private LocalDateTime timestamp;

    public String getPageId() {
        return pageId;
    }

    public void setPageId(String pageId) {
        this.pageId = pageId;
    }

    public String getActionType() {
        return actionType;
    }

    public void setActionType(String actionType) {
        this.actionType = actionType;
    }

    public Map<String, Object> getActionData() {
        return actionData;
    }

    public void setActionData(Map<String, Object> actionData) {
        this.actionData = actionData;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
} 