package com.yuzhi.prs.service.web;

import com.yuzhi.prs.service.domain.MaintenanceRecord;
import com.yuzhi.prs.service.domain.ServiceFeedEntry;
import com.yuzhi.prs.service.domain.SupervisionRecord;
import com.yuzhi.prs.service.domain.WateringRecord;
import com.yuzhi.prs.service.service.ServiceFeedService;
import java.time.LocalDate;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/service-records/maintenance")
public class MaintenanceRecordResource {

    private final ServiceFeedService serviceFeedService;

    public MaintenanceRecordResource(ServiceFeedService serviceFeedService) {
        this.serviceFeedService = serviceFeedService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MaintenanceRecordResponse create(@RequestBody CreateMaintenanceRecordRequest request) {
        MaintenanceRecord maintenanceRecord = new MaintenanceRecord(
            request.recordId(),
            request.projectId(),
            request.projectName(),
            request.positionId(),
            request.positionName(),
            request.assetId(),
            request.plantName(),
            request.completedBy(),
            request.completedByName(),
            LocalDate.parse(request.serviceDate()),
            request.notes(),
            new WateringRecord(request.wateringStatus(), request.waterVolumeLiters()),
            new SupervisionRecord(request.supervisionStatus(), request.issuesFound()),
            request.customerVisibleSummary()
        );

        ServiceFeedService.RecordedMaintenance result = serviceFeedService.recordMaintenance(maintenanceRecord);

        return new MaintenanceRecordResponse(
            result.maintenanceRecord().id(),
            result.maintenanceRecord().projectId(),
            result.maintenanceRecord().projectName(),
            result.maintenanceRecord().positionId(),
            result.maintenanceRecord().positionName(),
            result.maintenanceRecord().assetId(),
            result.maintenanceRecord().plantName(),
            result.maintenanceRecord().completedBy(),
            result.maintenanceRecord().completedByName(),
            result.maintenanceRecord().serviceDate(),
            result.maintenanceRecord().notes(),
            result.maintenanceRecord().watering(),
            result.maintenanceRecord().supervision(),
            result.serviceFeedEntry()
        );
    }

    public record CreateMaintenanceRecordRequest(
        String recordId,
        String projectId,
        String projectName,
        String positionId,
        String positionName,
        String assetId,
        String plantName,
        String completedBy,
        String completedByName,
        String serviceDate,
        String notes,
        String wateringStatus,
        double waterVolumeLiters,
        String supervisionStatus,
        int issuesFound,
        String customerVisibleSummary
    ) {}

    public record MaintenanceRecordResponse(
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
        ServiceFeedEntry serviceFeedEntry
    ) {}
}
