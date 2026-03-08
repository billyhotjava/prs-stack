package com.yuzhi.prs.service.web;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.yuzhi.prs.PrsBackendApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest(classes = PrsBackendApplication.class)
@AutoConfigureMockMvc
class MaintenanceRecordResourceIT {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void createsCustomerVisibleServiceFeedEntryFromFieldMaintenance() throws Exception {
        mockMvc.perform(post("/api/service-records/maintenance")
                .header("X-PRS-User-Id", "user-123")
                .header("X-PRS-Roles", "maintenance,supervision")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {
                      "recordId": "SRV-001",
                      "projectId": "PRJ-001",
                      "projectName": "Shanghai IFC Tower",
                      "positionId": "POS-001",
                      "positionName": "Reception East",
                      "assetId": "AST-001",
                      "plantName": "Ficus lyrata",
                      "completedBy": "EMP-001",
                      "completedByName": "Li Wei",
                      "serviceDate": "2026-03-08",
                      "notes": "Trimmed damaged leaves and refreshed soil",
                      "wateringStatus": "completed",
                      "waterVolumeLiters": 3.5,
                      "supervisionStatus": "passed",
                      "issuesFound": 0,
                      "customerVisibleSummary": "Watering completed and plant condition verified"
                    }
                    """))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.id").value("SRV-001"))
            .andExpect(jsonPath("$.watering.status").value("completed"))
            .andExpect(jsonPath("$.supervision.status").value("passed"))
            .andExpect(jsonPath("$.serviceFeedEntry.entryType").value("maintenance"))
            .andExpect(jsonPath("$.serviceFeedEntry.customerVisible").value(true))
            .andExpect(jsonPath("$.serviceFeedEntry.summary").value("Watering completed and plant condition verified"));

        mockMvc.perform(get("/api/service-feed/projects/PRJ-001")
                .header("X-PRS-User-Id", "user-123")
                .header("X-PRS-Roles", "maintenance,supervision,customer"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].entryType").value("maintenance"))
            .andExpect(jsonPath("$[0].projectName").value("Shanghai IFC Tower"))
            .andExpect(jsonPath("$[0].positionName").value("Reception East"))
            .andExpect(jsonPath("$[0].summary").value("Watering completed and plant condition verified"));
    }
}
