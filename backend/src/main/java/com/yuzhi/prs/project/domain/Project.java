package com.yuzhi.prs.project.domain;

import com.yuzhi.prs.contract.domain.Contract;
import com.yuzhi.prs.customer.domain.Customer;

public record Project(
    String id,
    String code,
    String name,
    Customer customer,
    Contract contract,
    boolean profitCenter,
    boolean serviceCenter
) {}
