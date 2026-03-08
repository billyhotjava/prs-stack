package com.yuzhi.prs.skill.service;

import com.yuzhi.prs.skill.api.SkillExecuteRequest;
import com.yuzhi.prs.skill.api.SkillExecuteResponse;
import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class SkillRouterService {

    private final PlantIssueDraftSkillService plantIssueDraftSkillService;
    private final FeedbackRoutingSkillService feedbackRoutingSkillService;
    private final ReconciliationExplainSkillService reconciliationExplainSkillService;

    public SkillRouterService(
        PlantIssueDraftSkillService plantIssueDraftSkillService,
        FeedbackRoutingSkillService feedbackRoutingSkillService,
        ReconciliationExplainSkillService reconciliationExplainSkillService
    ) {
        this.plantIssueDraftSkillService = plantIssueDraftSkillService;
        this.feedbackRoutingSkillService = feedbackRoutingSkillService;
        this.reconciliationExplainSkillService = reconciliationExplainSkillService;
    }

    public SkillExecuteResponse execute(SkillExecuteRequest request) {
        String skillId = request.skillId() == null || request.skillId().isBlank()
            ? "unknown-skill"
            : request.skillId();

        return switch (skillId) {
            case "prs-plant-issue-draft" -> plantIssueDraftSkillService.execute(request);
            case "prs-feedback-routing" -> feedbackRoutingSkillService.execute(request);
            case "prs-reconciliation-explain" -> reconciliationExplainSkillService.execute(request);
            default -> defaultResponse(skillId, request);
        };
    }

    private SkillExecuteResponse defaultResponse(String skillId, SkillExecuteRequest request) {
        Map<String, Object> responseData = new LinkedHashMap<>();
        responseData.put("skillId", skillId);
        responseData.put("recommendedAction", "review-dispatch");
        responseData.put("params", request.safeParams());
        responseData.put("context", request.safeContext());

        return new SkillExecuteResponse(
            responseData,
            "Prepared PRS action plan for skill " + skillId
        );
    }
}
