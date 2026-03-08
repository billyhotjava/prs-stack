package com.yuzhi.prs.change.domain;

public record ApprovalDecision(
    String requestId,
    String approverId,
    String approverName,
    String decision,
    String comment
) {}
