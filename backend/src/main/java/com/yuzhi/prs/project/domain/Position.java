package com.yuzhi.prs.project.domain;

import com.yuzhi.prs.customer.domain.CustomerContact;

public record Position(
    String id,
    String projectId,
    Building building,
    Floor floor,
    String code,
    String displayName,
    CustomerContact primaryContact
) {}
