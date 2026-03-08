package com.yuzhi.prs.finance.domain;

import java.math.BigDecimal;
import java.time.YearMonth;

public record ReimbursementFact(
    String id,
    String projectId,
    YearMonth accountingPeriod,
    BigDecimal amount,
    String currency,
    String payeeName,
    String expenseType
) {}
