package com.yuzhi.prs.service.domain;

import java.time.LocalDate;

public record ServiceFeedEntry(
    String id,
    String entryType,
    String projectId,
    String projectName,
    String positionId,
    String positionName,
    String summary,
    LocalDate serviceDate,
    boolean customerVisible
) {}
