package com.yuzhi.prs.change.service;

import com.yuzhi.prs.change.domain.ApprovalDecision;
import com.yuzhi.prs.change.domain.PlantChangeRequest;
import org.springframework.stereotype.Service;

@Service
public class PlantChangeService {

    public PlantChangeRequest createDraft(
        String requestId,
        String projectId,
        String positionId,
        String assetId,
        String requestType,
        String intakeMode,
        String draftSummary
    ) {
        return new PlantChangeRequest(
            requestId,
            projectId,
            positionId,
            assetId,
            requestType,
            intakeMode,
            draftSummary,
            true,
            "draft",
            null
        );
    }

    public PlantChangeRequest submitForApproval(PlantChangeRequest request) {
        return new PlantChangeRequest(
            request.id(),
            request.projectId(),
            request.positionId(),
            request.assetId(),
            request.requestType(),
            request.intakeMode(),
            request.draftSummary(),
            request.supervisorApprovalRequired(),
            "pending-supervisor-approval",
            request.approvalDecision()
        );
    }

    public PlantChangeRequest recordDecision(PlantChangeRequest request, ApprovalDecision approvalDecision) {
        String status = "approved".equalsIgnoreCase(approvalDecision.decision()) ? "approved" : "rejected";
        return new PlantChangeRequest(
            request.id(),
            request.projectId(),
            request.positionId(),
            request.assetId(),
            request.requestType(),
            request.intakeMode(),
            request.draftSummary(),
            request.supervisorApprovalRequired(),
            status,
            approvalDecision
        );
    }
}
