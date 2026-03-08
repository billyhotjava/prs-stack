package com.yuzhi.prs.skill.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.yuzhi.prs.skill.api.SkillExecuteRequest;
import com.yuzhi.prs.skill.api.SkillExecuteResponse;
import java.util.Map;
import org.junit.jupiter.api.Test;

class SkillRouterServiceTest {

    @Test
    void routesPlantIssueDraftSkillToAiSuggestionHandler() {
        SkillRouterService service = new SkillRouterService(
            new PlantIssueDraftSkillService(),
            new FeedbackRoutingSkillService(),
            new ReconciliationExplainSkillService()
        );

        SkillExecuteResponse response = service.execute(new SkillExecuteRequest(
            "prs-plant-issue-draft",
            Map.of("issue", "leaf browning", "projectId", "PRJ-001"),
            Map.of("userId", "user-123")
        ));

        assertThat(response.summary()).isEqualTo("Drafted plant issue response for PRS");
        assertThat(response.data()).containsEntry("recommendedAction", "submit-supervisor-review");
    }

    @Test
    void routesFeedbackSkillToOperationsSuggestionHandler() {
        SkillRouterService service = new SkillRouterService(
            new PlantIssueDraftSkillService(),
            new FeedbackRoutingSkillService(),
            new ReconciliationExplainSkillService()
        );

        SkillExecuteResponse response = service.execute(new SkillExecuteRequest(
            "prs-feedback-routing",
            Map.of("feedbackType", "complaint"),
            Map.of()
        ));

        assertThat(response.summary()).isEqualTo("Prepared feedback routing explanation for PRS");
        assertThat(response.data()).containsEntry("recommendedAction", "operations-review");
    }

    @Test
    void routesReconciliationSkillToFinanceExplanationHandler() {
        SkillRouterService service = new SkillRouterService(
            new PlantIssueDraftSkillService(),
            new FeedbackRoutingSkillService(),
            new ReconciliationExplainSkillService()
        );

        SkillExecuteResponse response = service.execute(new SkillExecuteRequest(
            "prs-reconciliation-explain",
            Map.of("differenceType", "Amount mismatch"),
            Map.of()
        ));

        assertThat(response.summary()).isEqualTo("Prepared reconciliation explanation for PRS");
        assertThat(response.data()).containsEntry("recommendedAction", "finance-review");
    }
}
