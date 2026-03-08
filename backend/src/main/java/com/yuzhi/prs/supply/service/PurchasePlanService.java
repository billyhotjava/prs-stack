package com.yuzhi.prs.supply.service;

import com.yuzhi.prs.change.domain.PlantChangeRequest;
import com.yuzhi.prs.finance.domain.FactSourceType;
import com.yuzhi.prs.finance.domain.ProjectFinanceFact;
import com.yuzhi.prs.finance.service.ProjectFinanceFactService;
import com.yuzhi.prs.supply.domain.PurchasePlan;
import java.math.BigDecimal;
import java.time.YearMonth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PurchasePlanService {

    private final ProjectFinanceFactService projectFinanceFactService;

    public PurchasePlanService() {
        this(new ProjectFinanceFactService());
    }

    @Autowired
    public PurchasePlanService(ProjectFinanceFactService projectFinanceFactService) {
        this.projectFinanceFactService = projectFinanceFactService;
    }

    public PurchasePlan createPlannedPurchase(PlantChangeRequest request, int quantity) {
        PurchasePlan purchasePlan = new PurchasePlan(
            "PLAN-" + request.id(),
            request.id(),
            request.projectId(),
            request.requestType(),
            quantity,
            "planned"
        );
        projectFinanceFactService.recordFact(new ProjectFinanceFact(
            "FACT-" + purchasePlan.id(),
            request.projectId(),
            YearMonth.now(),
            "cost",
            FactSourceType.PURCHASE_PLAN,
            purchasePlan.id(),
            BigDecimal.valueOf(quantity).multiply(new BigDecimal("120.00")),
            "CNY",
            "procurement"
        ));
        return purchasePlan;
    }
}
