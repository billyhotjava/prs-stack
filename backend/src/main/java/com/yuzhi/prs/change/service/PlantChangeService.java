package com.yuzhi.prs.change.service;

import com.yuzhi.prs.change.domain.ApprovalDecision;
import com.yuzhi.prs.change.domain.PlantChangeRequest;
import com.yuzhi.prs.finance.domain.FactSourceType;
import com.yuzhi.prs.finance.domain.ProjectFinanceFact;
import com.yuzhi.prs.finance.service.ProjectFinanceFactService;
import java.math.BigDecimal;
import java.time.YearMonth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlantChangeService {

    private final ProjectFinanceFactService projectFinanceFactService;

    public PlantChangeService() {
        this(new ProjectFinanceFactService());
    }

    @Autowired
    public PlantChangeService(ProjectFinanceFactService projectFinanceFactService) {
        this.projectFinanceFactService = projectFinanceFactService;
    }

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
        PlantChangeRequest decided = new PlantChangeRequest(
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
        if ("approved".equals(status)) {
            projectFinanceFactService.recordFact(new ProjectFinanceFact(
                "FACT-" + request.id(),
                request.projectId(),
                YearMonth.now(),
                "cost",
                FactSourceType.PLANT_CHANGE,
                request.id(),
                estimateChangeCost(request.requestType()),
                "CNY",
                "operations"
            ));
        }
        return decided;
    }

    private BigDecimal estimateChangeCost(String requestType) {
        return switch (requestType) {
            case "replacement" -> new BigDecimal("300.00");
            case "addition" -> new BigDecimal("220.00");
            case "removal" -> new BigDecimal("80.00");
            default -> new BigDecimal("150.00");
        };
    }
}
