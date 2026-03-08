package com.yuzhi.prs.finance.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.yuzhi.prs.finance.domain.FactSourceType;
import com.yuzhi.prs.finance.domain.ProjectFinanceFact;
import java.math.BigDecimal;
import java.time.YearMonth;
import org.junit.jupiter.api.Test;

class ProjectFinanceFactServiceTest {

    @Test
    void upsertsFactsByProjectPeriodTypeAndSourceDocument() {
        ProjectFinanceFactService service = new ProjectFinanceFactService();

        ProjectFinanceFact original = service.recordFact(new ProjectFinanceFact(
            "FACT-001",
            "PRJ-001",
            YearMonth.of(2026, 3),
            "cost",
            FactSourceType.PLANT_CHANGE,
            "CHG-001",
            new BigDecimal("280.00"),
            "CNY",
            "operations"
        ));
        ProjectFinanceFact updated = service.recordFact(new ProjectFinanceFact(
            "FACT-002",
            "PRJ-001",
            YearMonth.of(2026, 3),
            "cost",
            FactSourceType.PLANT_CHANGE,
            "CHG-001",
            new BigDecimal("300.00"),
            "CNY",
            "operations"
        ));

        assertThat(original.reconciliationKey()).isEqualTo("PRJ-001|2026-03|cost|PLANT_CHANGE|CHG-001");
        assertThat(service.listProjectFacts("PRJ-001")).singleElement().isEqualTo(updated);
        assertThat(updated.amount()).isEqualByComparingTo("300.00");
    }
}
