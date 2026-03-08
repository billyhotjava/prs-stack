package com.yuzhi.prs.customer.domain;

import java.util.List;

public record CustomerPortalAccount(
    String id,
    String customerId,
    String customerName,
    String contactName,
    List<String> visibleProjectIds,
    boolean active
) {}
