package com.yuzhi.prs.placement.domain;

public record InitialPlacementPlan(
    String id,
    String projectId,
    int plannedAssetCount,
    String lifecycleStage,
    String status
) {}
