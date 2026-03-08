package com.yuzhi.prs.finance.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.yuzhi.prs.finance.domain.FactSourceType;
import com.yuzhi.prs.finance.domain.ManualLedgerEntry;
import com.yuzhi.prs.finance.domain.ProjectFinanceFact;
import com.yuzhi.prs.finance.domain.ReconciliationDifference;
import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.List;
import org.junit.jupiter.api.Test;

class ReconciliationServiceTest {

    @Test
    void identifiesAmountDifferencesByProjectPeriodAndSourceDocument() {
        ReconciliationService service = new ReconciliationService();

        List<ReconciliationDifference> differences = service.reconcile(
            List.of(new ProjectFinanceFact(
                "FACT-001",
                "PRJ-001",
                YearMonth.of(2026, 3),
                "cost",
                FactSourceType.PURCHASE_PLAN,
                "PLAN-001",
                new BigDecimal("240.00"),
                "CNY",
                "procurement"
            )),
            List.of(new ManualLedgerEntry(
                "LEDGER-001",
                "PRJ-001",
                YearMonth.of(2026, 3),
                "cost",
                FactSourceType.PURCHASE_PLAN,
                "PLAN-001",
                new BigDecimal("250.00"),
                "CNY",
                "finance"
            ))
        );

        assertThat(differences).singleElement().satisfies(difference -> {
            assertThat(difference.projectId()).isEqualTo("PRJ-001");
            assertThat(difference.accountingPeriod()).isEqualTo(YearMonth.of(2026, 3));
            assertThat(difference.sourceDocumentId()).isEqualTo("PLAN-001");
            assertThat(difference.responsibleRole()).isEqualTo("procurement");
            assertThat(difference.differenceAmount()).isEqualByComparingTo("-10.00");
        });
    }
}
