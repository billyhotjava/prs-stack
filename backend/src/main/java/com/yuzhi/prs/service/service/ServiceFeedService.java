package com.yuzhi.prs.service.service;

import com.yuzhi.prs.finance.domain.FactSourceType;
import com.yuzhi.prs.finance.domain.ProjectFinanceFact;
import com.yuzhi.prs.finance.service.ProjectFinanceFactService;
import com.yuzhi.prs.service.domain.MaintenanceRecord;
import com.yuzhi.prs.service.domain.ServiceFeedEntry;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.time.YearMonth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServiceFeedService {

    private final Map<String, List<ServiceFeedEntry>> entriesByProject = new ConcurrentHashMap<>();
    private final ProjectFinanceFactService projectFinanceFactService;

    public ServiceFeedService() {
        this(new ProjectFinanceFactService());
    }

    @Autowired
    public ServiceFeedService(ProjectFinanceFactService projectFinanceFactService) {
        this.projectFinanceFactService = projectFinanceFactService;
    }

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
        projectFinanceFactService.recordFact(new ProjectFinanceFact(
            "FACT-" + maintenanceRecord.id(),
            maintenanceRecord.projectId(),
            YearMonth.from(maintenanceRecord.serviceDate()),
            "cost",
            FactSourceType.SERVICE_EVENT,
            maintenanceRecord.id(),
            maintenanceCost(maintenanceRecord),
            "CNY",
            "maintenance"
        ));

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

    private BigDecimal maintenanceCost(MaintenanceRecord maintenanceRecord) {
        return BigDecimal.valueOf(maintenanceRecord.watering().waterVolumeLiters()).multiply(new BigDecimal("20.00"));
    }
}
