package com.yuzhi.prs.finance.web;

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
class FinanceFactResourceIT {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void recordsBusinessFinanceFactsAndListsProjectView() throws Exception {
        mockMvc.perform(post("/api/finance/facts/invoice")
                .header("X-PRS-User-Id", "finance-001")
                .header("X-PRS-Roles", "finance")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {
                      "factId": "INV-001",
                      "projectId": "PRJ-001",
                      "accountingPeriod": "2026-03",
                      "amount": 1200.00,
                      "currency": "CNY",
                      "invoiceNumber": "FP-2026-001",
                      "customerName": "IFC Property"
                    }
                    """))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.invoiceNumber").value("FP-2026-001"));

        mockMvc.perform(post("/api/finance/facts/collection")
                .header("X-PRS-User-Id", "finance-001")
                .header("X-PRS-Roles", "finance")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {
                      "factId": "COL-001",
                      "projectId": "PRJ-001",
                      "accountingPeriod": "2026-03",
                      "amount": 800.00,
                      "currency": "CNY",
                      "payerName": "IFC Property",
                      "referenceNumber": "HK-2026-001"
                    }
                    """))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.referenceNumber").value("HK-2026-001"));

        mockMvc.perform(get("/api/finance/facts/projects/PRJ-001")
                .header("X-PRS-User-Id", "finance-001")
                .header("X-PRS-Roles", "finance"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.invoiceFacts[0].invoiceNumber").value("FP-2026-001"))
            .andExpect(jsonPath("$.collectionFacts[0].referenceNumber").value("HK-2026-001"))
            .andExpect(jsonPath("$.ledgerFacts[0].projectId").value("PRJ-001"));
    }
}
