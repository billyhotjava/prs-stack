package com.yuzhi.prs.change.domain;

public record PlantChangeRequest(
    String id,
    String projectId,
    String positionId,
    String assetId,
    String requestType,
    String intakeMode,
    String draftSummary,
    boolean supervisorApprovalRequired,
    String status,
    ApprovalDecision approvalDecision
) {}
