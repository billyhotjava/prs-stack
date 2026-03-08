package com.yuzhi.prs.supply.service;

import com.yuzhi.prs.finance.domain.FactSourceType;
import com.yuzhi.prs.finance.domain.ProjectFinanceFact;
import com.yuzhi.prs.finance.service.ProjectFinanceFactService;
import com.yuzhi.prs.supply.domain.DeliveryOrder;
import com.yuzhi.prs.supply.domain.RecoveryOrder;
import java.math.BigDecimal;
import java.time.YearMonth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeliveryOrderService {

    private final ProjectFinanceFactService projectFinanceFactService;

    public DeliveryOrderService() {
        this(new ProjectFinanceFactService());
    }

    @Autowired
    public DeliveryOrderService(ProjectFinanceFactService projectFinanceFactService) {
        this.projectFinanceFactService = projectFinanceFactService;
    }

    public DeliveryOrder scheduleDelivery(String deliveryOrderId, String projectId, String positionId, int quantity) {
        DeliveryOrder deliveryOrder = new DeliveryOrder(deliveryOrderId, projectId, positionId, quantity, "scheduled");
        projectFinanceFactService.recordFact(new ProjectFinanceFact(
            "FACT-" + deliveryOrder.id(),
            projectId,
            YearMonth.now(),
            "cost",
            FactSourceType.DELIVERY_ORDER,
            deliveryOrder.id(),
            BigDecimal.valueOf(quantity).multiply(new BigDecimal("35.00")),
            "CNY",
            "operations"
        ));
        return deliveryOrder;
    }

    public RecoveryOrder scheduleRecovery(String recoveryOrderId, String projectId, String positionId, String assetId) {
        RecoveryOrder recoveryOrder = new RecoveryOrder(recoveryOrderId, projectId, positionId, assetId, "scheduled");
        projectFinanceFactService.recordFact(new ProjectFinanceFact(
            "FACT-" + recoveryOrder.id(),
            projectId,
            YearMonth.now(),
            "cost",
            FactSourceType.RECOVERY_ORDER,
            recoveryOrder.id(),
            new BigDecimal("45.00"),
            "CNY",
            "maintenance"
        ));
        return recoveryOrder;
    }
}
