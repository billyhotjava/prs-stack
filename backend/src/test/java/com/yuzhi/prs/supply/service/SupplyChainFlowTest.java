package com.yuzhi.prs.supply.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.yuzhi.prs.change.domain.PlantChangeRequest;
import com.yuzhi.prs.task.service.TaskAssignmentPolicyService;
import com.yuzhi.prs.task.service.TaskDecompositionService;
import org.junit.jupiter.api.Test;

class SupplyChainFlowTest {

    @Test
    void generatesExecutableSupplyOrdersFromApprovedPlantChange() {
        TaskDecompositionService service = new TaskDecompositionService(new TaskAssignmentPolicyService());
        PlantChangeRequest approvedRequest = new PlantChangeRequest(
            "CHG-001",
            "PRJ-001",
            "POS-001",
            "AST-001",
            "replacement",
            "photo+voice",
            "Replace the ficus after browning",
            true,
            "approved",
            null
        );

        TaskDecompositionService.SupplyExecutionPlan executionPlan = service.decomposeApprovedChange(approvedRequest, 1);

        assertThat(executionPlan.purchasePlan().status()).isEqualTo("planned");
        assertThat(executionPlan.purchaseOrder().status()).isEqualTo("created");
        assertThat(executionPlan.deliveryOrder().status()).isEqualTo("scheduled");
        assertThat(executionPlan.recoveryOrder().status()).isEqualTo("scheduled");
        assertThat(executionPlan.tasks())
            .extracting(TaskDecompositionService.ExecutableTask::assigneeRole)
            .containsExactly("procurement", "operations", "maintenance");
    }
}
