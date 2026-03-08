package com.yuzhi.prs.project.web;

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
class ProjectResourceIT {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void createsProjectAsProfitAndServiceCenter() throws Exception {
        mockMvc.perform(post("/api/projects")
                .header("X-PRS-User-Id", "user-123")
                .header("X-PRS-Roles", "operations,department-supervisor")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {
                      "projectCode": "PRJ-001",
                      "projectName": "Shanghai IFC Tower",
                      "customerId": "CUS-001",
                      "customerName": "IFC Property",
                      "contractId": "CON-001",
                      "contractCode": "HT-2026-001"
                    }
                    """))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.id").value("PRJ-001"))
            .andExpect(jsonPath("$.name").value("Shanghai IFC Tower"))
            .andExpect(jsonPath("$.customer.name").value("IFC Property"))
            .andExpect(jsonPath("$.contract.contractCode").value("HT-2026-001"))
            .andExpect(jsonPath("$.profitCenter").value(true))
            .andExpect(jsonPath("$.serviceCenter").value(true));
    }
}
