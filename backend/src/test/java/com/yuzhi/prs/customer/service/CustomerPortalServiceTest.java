package com.yuzhi.prs.customer.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.yuzhi.prs.service.domain.MaintenanceRecord;
import com.yuzhi.prs.service.domain.SupervisionRecord;
import com.yuzhi.prs.service.domain.WateringRecord;
import com.yuzhi.prs.service.service.ServiceFeedService;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.Test;

class CustomerPortalServiceTest {

    @Test
    void limitsPortalVisibilityToAssignedProjects() {
        ServiceFeedService serviceFeedService = new ServiceFeedService();
        CustomerPortalService customerPortalService = new CustomerPortalService(serviceFeedService);

        customerPortalService.registerAccount(
            "PORTAL-001",
            "CUS-001",
            "IFC Property",
            "Ms. Lin",
            List.of("PRJ-001", "PRJ-002")
        );

        serviceFeedService.recordMaintenance(new MaintenanceRecord(
            "SRV-001",
            "PRJ-001",
            "Shanghai IFC Tower",
            "POS-001",
            "Reception East",
            "AST-001",
            "Ficus lyrata",
            "EMP-001",
            "Li Wei",
            LocalDate.parse("2026-03-08"),
            "Trimmed damaged leaves",
            new WateringRecord("completed", 3.5),
            new SupervisionRecord("passed", 0),
            "Watering completed and plant condition verified"
        ));

        serviceFeedService.recordMaintenance(new MaintenanceRecord(
            "SRV-002",
            "PRJ-999",
            "Outside Project",
            "POS-999",
            "Not Visible",
            "AST-999",
            "Unknown",
            "EMP-999",
            "Outside User",
            LocalDate.parse("2026-03-08"),
            "Should stay hidden",
            new WateringRecord("completed", 1.0),
            new SupervisionRecord("passed", 0),
            "Hidden summary"
        ));

        CustomerPortalService.CustomerOverview overview = customerPortalService.buildOverview("CUS-001");

        assertThat(overview.customerName()).isEqualTo("IFC Property");
        assertThat(overview.projectCount()).isEqualTo(2);
        assertThat(overview.visibleFeedCount()).isEqualTo(1);
        assertThat(overview.latestSummary()).isEqualTo("Watering completed and plant condition verified");
        assertThat(customerPortalService.listServiceHistory("CUS-001"))
            .singleElement()
            .satisfies(entry -> {
                assertThat(entry.projectId()).isEqualTo("PRJ-001");
                assertThat(entry.summary()).isEqualTo("Watering completed and plant condition verified");
            });
    }
}
