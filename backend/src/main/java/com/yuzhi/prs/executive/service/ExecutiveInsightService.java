package com.yuzhi.prs.executive.service;

import com.yuzhi.prs.finance.domain.ProjectFinanceFact;
import com.yuzhi.prs.finance.domain.ReconciliationDifference;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ExecutiveInsightService {

    public ExecutiveSnapshot buildSnapshot(
        List<ProjectFinanceFact> financeFacts,
        List<ReconciliationDifference> reconciliationDifferences,
        int complaintTrendCount
    ) {
        BigDecimal monthlyRevenue = financeFacts.stream()
            .filter(fact -> "invoice".equalsIgnoreCase(fact.factType()) || "collection".equalsIgnoreCase(fact.factType()))
            .map(ProjectFinanceFact::amount)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal monthlyCost = financeFacts.stream()
            .filter(fact -> "cost".equalsIgnoreCase(fact.factType())
                || "reimbursement".equalsIgnoreCase(fact.factType())
                || "advance".equalsIgnoreCase(fact.factType()))
            .map(ProjectFinanceFact::amount)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal grossMarginRate = monthlyRevenue.compareTo(BigDecimal.ZERO) == 0
            ? BigDecimal.ZERO
            : monthlyRevenue.subtract(monthlyCost).divide(monthlyRevenue, 4, RoundingMode.HALF_UP);

        List<String> aiSummary = List.of(
            "Revenue and cost facts are now visible from the same operating ledger.",
            "Reconciliation risk needs review",
            "Open reconciliation items in scope: %s.".formatted(reconciliationDifferences.size()),
            "Complaint trend currently shows %s item(s) in attention scope.".formatted(complaintTrendCount)
        );

        return new ExecutiveSnapshot(
            monthlyRevenue,
            monthlyCost,
            grossMarginRate,
            reconciliationDifferences.size(),
            complaintTrendCount,
            aiSummary
        );
    }

    public record ExecutiveSnapshot(
        BigDecimal monthlyRevenue,
        BigDecimal monthlyCost,
        BigDecimal grossMarginRate,
        int reconciliationRiskCount,
        int complaintTrendCount,
        List<String> aiSummary
    ) {}
}
