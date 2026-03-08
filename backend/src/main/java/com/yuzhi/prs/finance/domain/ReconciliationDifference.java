package com.yuzhi.prs.finance.domain;

import java.math.BigDecimal;
import java.time.YearMonth;

public record ReconciliationDifference(
    String projectId,
    YearMonth accountingPeriod,
    String factType,
    FactSourceType sourceType,
    String sourceDocumentId,
    String differenceType,
    BigDecimal systemAmount,
    BigDecimal ledgerAmount,
    BigDecimal differenceAmount,
    String responsibleRole
) {}
