package com.yuzhi.prs.supply.domain;

public record RecoveryOrder(
    String id,
    String projectId,
    String positionId,
    String assetId,
    String status
) {}
