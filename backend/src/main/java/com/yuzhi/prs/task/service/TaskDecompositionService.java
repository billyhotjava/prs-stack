package com.yuzhi.prs.task.service;

import com.yuzhi.prs.change.domain.PlantChangeRequest;
import com.yuzhi.prs.supply.domain.DeliveryOrder;
import com.yuzhi.prs.supply.domain.PurchaseOrder;
import com.yuzhi.prs.supply.domain.PurchasePlan;
import com.yuzhi.prs.supply.domain.RecoveryOrder;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class TaskDecompositionService {

    private final TaskAssignmentPolicyService taskAssignmentPolicyService;

    public TaskDecompositionService(TaskAssignmentPolicyService taskAssignmentPolicyService) {
        this.taskAssignmentPolicyService = taskAssignmentPolicyService;
    }

    public SupplyExecutionPlan decomposeApprovedChange(PlantChangeRequest request, int quantity) {
        if (!"approved".equalsIgnoreCase(request.status())) {
            throw new IllegalStateException("Plant change must be approved before task decomposition");
        }

        PurchasePlan purchasePlan = new PurchasePlan(
            "PLAN-" + request.id(),
            request.id(),
            request.projectId(),
            request.requestType(),
            quantity,
            "planned"
        );
        PurchaseOrder purchaseOrder = new PurchaseOrder(
            "PO-" + request.id(),
            purchasePlan.id(),
            request.projectId(),
            quantity,
            "created"
        );
        DeliveryOrder deliveryOrder = new DeliveryOrder(
            "DO-" + request.id(),
            request.projectId(),
            request.positionId(),
            quantity,
            "scheduled"
        );
        RecoveryOrder recoveryOrder = new RecoveryOrder(
            "RO-" + request.id(),
            request.projectId(),
            request.positionId(),
            request.assetId(),
            "scheduled"
        );

        List<ExecutableTask> tasks = List.of(
            new ExecutableTask("TASK-PUR-" + request.id(), "purchase", taskAssignmentPolicyService.resolveAssigneeRole("purchase")),
            new ExecutableTask("TASK-DEL-" + request.id(), "delivery", taskAssignmentPolicyService.resolveAssigneeRole("delivery")),
            new ExecutableTask("TASK-REC-" + request.id(), "recovery", taskAssignmentPolicyService.resolveAssigneeRole("recovery"))
        );

        return new SupplyExecutionPlan(purchasePlan, purchaseOrder, deliveryOrder, recoveryOrder, tasks);
    }

    public record SupplyExecutionPlan(
        PurchasePlan purchasePlan,
        PurchaseOrder purchaseOrder,
        DeliveryOrder deliveryOrder,
        RecoveryOrder recoveryOrder,
        List<ExecutableTask> tasks
    ) {}

    public record ExecutableTask(
        String id,
        String taskType,
        String assigneeRole
    ) {}
}
