package com.yuzhi.prs.asset.domain;

public record AssetMovement(
    String assetId,
    String movementType,
    String fromStatus,
    String toStatus,
    String projectId,
    String positionId,
    String note
) {}
