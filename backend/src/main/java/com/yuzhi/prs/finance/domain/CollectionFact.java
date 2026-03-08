package com.yuzhi.prs.finance.domain;

import java.math.BigDecimal;
import java.time.YearMonth;

public record CollectionFact(
    String id,
    String projectId,
    YearMonth accountingPeriod,
    BigDecimal amount,
    String currency,
    String payerName,
    String referenceNumber
) {}
