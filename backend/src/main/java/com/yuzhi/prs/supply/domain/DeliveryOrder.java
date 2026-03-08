package com.yuzhi.prs.supply.domain;

public record DeliveryOrder(
    String id,
    String projectId,
    String positionId,
    int quantity,
    String status
) {}
