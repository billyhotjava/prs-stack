package com.yuzhi.prs.finance.domain;

import java.math.BigDecimal;
import java.time.YearMonth;

public record ManualLedgerEntry(
    String id,
    String projectId,
    YearMonth accountingPeriod,
    String factType,
    FactSourceType sourceType,
    String sourceDocumentId,
    BigDecimal amount,
    String currency,
    String responsibleRole
) {

    public String reconciliationKey() {
        return "%s|%s|%s|%s|%s".formatted(
            projectId,
            accountingPeriod,
            factType,
            sourceType,
            sourceDocumentId
        );
    }
}
