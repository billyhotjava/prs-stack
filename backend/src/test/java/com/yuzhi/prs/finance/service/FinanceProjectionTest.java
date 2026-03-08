package com.yuzhi.prs.finance.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.yuzhi.prs.change.domain.ApprovalDecision;
import com.yuzhi.prs.change.domain.PlantChangeRequest;
import com.yuzhi.prs.change.service.PlantChangeService;
import com.yuzhi.prs.finance.domain.ProjectFinanceFact;
import com.yuzhi.prs.service.domain.MaintenanceRecord;
import com.yuzhi.prs.service.domain.SupervisionRecord;
import com.yuzhi.prs.service.domain.WateringRecord;
import com.yuzhi.prs.service.service.ServiceFeedService;
import com.yuzhi.prs.supply.domain.DeliveryOrder;
import com.yuzhi.prs.supply.domain.PurchasePlan;
import com.yuzhi.prs.supply.domain.RecoveryOrder;
import com.yuzhi.prs.supply.service.DeliveryOrderService;
import com.yuzhi.prs.supply.service.PurchasePlanService;
import java.time.LocalDate;
import java.time.YearMonth;
import org.junit.jupiter.api.Test;

class FinanceProjectionTest {

    @Test
    void projectsApprovedOperationsIntoDeterministicFinanceFacts() {
        ProjectFinanceFactService ledger = new ProjectFinanceFactService();
        PlantChangeService plantChangeService = new PlantChangeService(ledger);
        PurchasePlanService purchasePlanService = new PurchasePlanService(ledger);
        DeliveryOrderService deliveryOrderService = new DeliveryOrderService(ledger);
        ServiceFeedService serviceFeedService = new ServiceFeedService(ledger);

        PlantChangeRequest approvedChange = plantChangeService.recordDecision(
            plantChangeService.submitForApproval(
                plantChangeService.createDraft("CHG-001", "PRJ-001", "POS-001", "AST-001", "replacement", "photo+voice", "Replace lobby ficus")
            ),
            new ApprovalDecision("CHG-001", "SUP-001", "Zhang Min", "approved", "Proceed")
        );
        PurchasePlan purchasePlan = purchasePlanService.createPlannedPurchase(approvedChange, 2);
        DeliveryOrder deliveryOrder = deliveryOrderService.scheduleDelivery("DO-001", "PRJ-001", "POS-001", 2);
        RecoveryOrder recoveryOrder = deliveryOrderService.scheduleRecovery("RO-001", "PRJ-001", "POS-001", "AST-001");
        serviceFeedService.recordMaintenance(new MaintenanceRecord(
            "SRV-001",
            "PRJ-001",
            "Shanghai IFC Tower",
            "POS-001",
            "Reception East",
            "AST-001",
            "Ficus lyrata",
            "EMP-001",
            "Li Wei",
            LocalDate.of(2026, 3, 9),
            "Watered and verified",
            new WateringRecord("completed", 3.0),
            new SupervisionRecord("passed", 0),
            "Watering completed and plant condition verified"
        ));

        assertThat(approvedChange.status()).isEqualTo("approved");
        assertThat(purchasePlan.status()).isEqualTo("planned");
        assertThat(deliveryOrder.status()).isEqualTo("scheduled");
        assertThat(recoveryOrder.status()).isEqualTo("scheduled");
        assertThat(ledger.listProjectFacts("PRJ-001"))
            .hasSize(5)
            .extracting(ProjectFinanceFact::reconciliationKey)
            .contains(
                "PRJ-001|2026-03|cost|PLANT_CHANGE|CHG-001",
                "PRJ-001|2026-03|cost|PURCHASE_PLAN|PLAN-CHG-001",
                "PRJ-001|2026-03|cost|DELIVERY_ORDER|DO-001",
                "PRJ-001|2026-03|cost|RECOVERY_ORDER|RO-001",
                "PRJ-001|2026-03|cost|SERVICE_EVENT|SRV-001"
            );
        assertThat(ledger.listProjectFacts("PRJ-001"))
            .extracting(ProjectFinanceFact::accountingPeriod)
            .containsOnly(YearMonth.of(2026, 3));
    }
}
