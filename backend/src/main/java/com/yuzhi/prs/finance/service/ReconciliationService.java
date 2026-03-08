package com.yuzhi.prs.finance.service;

import com.yuzhi.prs.finance.domain.ManualLedgerEntry;
import com.yuzhi.prs.finance.domain.ProjectFinanceFact;
import com.yuzhi.prs.finance.domain.ReconciliationDifference;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class ReconciliationService {

    public List<ReconciliationDifference> reconcile(
        List<ProjectFinanceFact> systemFacts,
        List<ManualLedgerEntry> manualLedgerEntries
    ) {
        Map<String, ManualLedgerEntry> ledgerByKey = new LinkedHashMap<>();
        for (ManualLedgerEntry entry : manualLedgerEntries) {
            ledgerByKey.put(entry.reconciliationKey(), entry);
        }

        List<ReconciliationDifference> differences = new ArrayList<>();
        for (ProjectFinanceFact fact : systemFacts) {
            ManualLedgerEntry ledgerEntry = ledgerByKey.remove(fact.reconciliationKey());
            if (ledgerEntry == null) {
                differences.add(new ReconciliationDifference(
                    fact.projectId(),
                    fact.accountingPeriod(),
                    fact.factType(),
                    fact.sourceType(),
                    fact.sourceDocumentId(),
                    "Missing in ledger",
                    fact.amount(),
                    BigDecimal.ZERO,
                    fact.amount(),
                    fact.responsibleRole()
                ));
                continue;
            }

            BigDecimal differenceAmount = fact.amount().subtract(ledgerEntry.amount());
            if (differenceAmount.compareTo(BigDecimal.ZERO) != 0) {
                differences.add(new ReconciliationDifference(
                    fact.projectId(),
                    fact.accountingPeriod(),
                    fact.factType(),
                    fact.sourceType(),
                    fact.sourceDocumentId(),
                    "Amount mismatch",
                    fact.amount(),
                    ledgerEntry.amount(),
                    differenceAmount,
                    fact.responsibleRole()
                ));
            }
        }

        for (ManualLedgerEntry unmatchedEntry : ledgerByKey.values()) {
            differences.add(new ReconciliationDifference(
                unmatchedEntry.projectId(),
                unmatchedEntry.accountingPeriod(),
                unmatchedEntry.factType(),
                unmatchedEntry.sourceType(),
                unmatchedEntry.sourceDocumentId(),
                "Missing in system",
                BigDecimal.ZERO,
                unmatchedEntry.amount(),
                BigDecimal.ZERO.subtract(unmatchedEntry.amount()),
                unmatchedEntry.responsibleRole()
            ));
        }

        return differences;
    }
}
