package com.yuzhi.prs.customer.domain;

public record CustomerContact(
    String id,
    String customerId,
    String name,
    String phone
) {}
