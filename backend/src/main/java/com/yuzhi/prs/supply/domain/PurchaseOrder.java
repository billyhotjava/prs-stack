package com.yuzhi.prs.supply.domain;

public record PurchaseOrder(
    String id,
    String purchasePlanId,
    String projectId,
    int quantity,
    String status
) {}
