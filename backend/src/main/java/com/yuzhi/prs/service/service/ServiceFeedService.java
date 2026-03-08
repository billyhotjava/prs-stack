package com.yuzhi.prs.service.service;

import com.yuzhi.prs.service.domain.MaintenanceRecord;
import com.yuzhi.prs.service.domain.ServiceFeedEntry;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Service;

@Service
public class ServiceFeedService {

    private final Map<String, List<ServiceFeedEntry>> entriesByProject = new ConcurrentHashMap<>();

    public RecordedMaintenance recordMaintenance(MaintenanceRecord maintenanceRecord) {
        ServiceFeedEntry serviceFeedEntry = new ServiceFeedEntry(
            "FEED-" + maintenanceRecord.id(),
            "maintenance",
            maintenanceRecord.projectId(),
            maintenanceRecord.projectName(),
            maintenanceRecord.positionId(),
            maintenanceRecord.positionName(),
            maintenanceRecord.customerVisibleSummary(),
            maintenanceRecord.serviceDate(),
            true
        );

        entriesByProject.computeIfAbsent(maintenanceRecord.projectId(), ignored -> new ArrayList<>()).add(serviceFeedEntry);

        return new RecordedMaintenance(maintenanceRecord, serviceFeedEntry);
    }

    public List<ServiceFeedEntry> listProjectFeed(String projectId) {
        return entriesByProject.getOrDefault(projectId, List.of()).stream()
            .sorted(Comparator.comparing(ServiceFeedEntry::serviceDate).reversed())
            .toList();
    }

    public record RecordedMaintenance(
        MaintenanceRecord maintenanceRecord,
        ServiceFeedEntry serviceFeedEntry
    ) {}
}
