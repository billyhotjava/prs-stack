package com.yuzhi.prs.task.service;

import org.springframework.stereotype.Service;

@Service
public class TaskAssignmentPolicyService {

    public String resolveAssigneeRole(String taskType) {
        return switch (taskType) {
            case "purchase" -> "procurement";
            case "delivery" -> "operations";
            case "recovery" -> "maintenance";
            default -> "operations";
        };
    }
}
