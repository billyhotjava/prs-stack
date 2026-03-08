package com.yuzhi.prs.service.domain;

import java.time.LocalDate;

public record MaintenanceRecord(
    String id,
    String projectId,
    String projectName,
    String positionId,
    String positionName,
    String assetId,
    String plantName,
    String completedBy,
    String completedByName,
    LocalDate serviceDate,
    String notes,
    WateringRecord watering,
    SupervisionRecord supervision,
    String customerVisibleSummary
) {}
