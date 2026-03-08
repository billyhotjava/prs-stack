package com.yuzhi.prs.service.domain;

import java.time.Instant;

public record CustomerFeedback(
    String id,
    String customerId,
    String projectId,
    String feedbackType,
    String title,
    String message,
    String requestedBy,
    Instant submittedAt
) {}
