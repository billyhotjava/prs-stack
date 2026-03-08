package com.yuzhi.prs.supply.domain;

public record PurchasePlan(
    String id,
    String changeRequestId,
    String projectId,
    String requestType,
    int quantity,
    String status
) {}
