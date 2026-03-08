package com.yuzhi.prs.executive.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.yuzhi.prs.finance.domain.FactSourceType;
import com.yuzhi.prs.finance.domain.ProjectFinanceFact;
import com.yuzhi.prs.finance.domain.ReconciliationDifference;
import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.List;
import org.junit.jupiter.api.Test;

class ExecutiveInsightServiceTest {

    @Test
    void summarizesProfitabilityServiceRiskAndFinanceExceptions() {
        ExecutiveInsightService service = new ExecutiveInsightService();

        ExecutiveInsightService.ExecutiveSnapshot snapshot = service.buildSnapshot(
            List.of(
                new ProjectFinanceFact("FACT-REV", "PRJ-001", YearMonth.of(2026, 3), "invoice", FactSourceType.INVOICE, "INV-001", new BigDecimal("1200.00"), "CNY", "finance"),
                new ProjectFinanceFact("FACT-COST", "PRJ-001", YearMonth.of(2026, 3), "cost", FactSourceType.PURCHASE_PLAN, "PLAN-001", new BigDecimal("800.00"), "CNY", "procurement")
            ),
            List.of(
                new ReconciliationDifference("PRJ-001", YearMonth.of(2026, 3), "cost", FactSourceType.PURCHASE_PLAN, "PLAN-001", "Amount mismatch", new BigDecimal("800.00"), new BigDecimal("820.00"), new BigDecimal("-20.00"), "procurement")
            ),
            2
        );

        assertThat(snapshot.monthlyRevenue()).isEqualByComparingTo("1200.00");
        assertThat(snapshot.grossMarginRate()).isEqualByComparingTo("0.3333");
        assertThat(snapshot.reconciliationRiskCount()).isEqualTo(1);
        assertThat(snapshot.complaintTrendCount()).isEqualTo(2);
        assertThat(snapshot.aiSummary()).contains("Reconciliation risk needs review");
    }
}
