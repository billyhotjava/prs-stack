package com.yuzhi.prs.asset.domain;

public record PlantAsset(
    String id,
    String plantName,
    String projectId,
    String positionId,
    String status
) {}
