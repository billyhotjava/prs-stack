package com.yuzhi.prs.placement.domain;

public record PlacementBatch(
    String id,
    String planId,
    String projectId,
    int assetCount,
    String lifecycleStage,
    String status
) {}
