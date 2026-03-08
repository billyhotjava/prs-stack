package com.yuzhi.prs.skill.web;

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
class SkillExecuteResourceIT {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void executesSkillRequestsThroughStableRpcContract() throws Exception {
        mockMvc.perform(post("/api/skill/execute")
                .header("X-PRS-User-Id", "user-123")
                .header("X-PRS-Roles", "operations,maintenance")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {
                      "skillId": "prs-operations-routing",
                      "params": {
                        "route": "report-change",
                        "projectId": "PRJ-001"
                      },
                      "context": {
                        "tenantId": "tenant-1",
                        "userId": "user-123",
                        "traceId": "trace-001"
                      }
                    }
                    """))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.summary").value("Prepared PRS action plan for skill prs-operations-routing"))
            .andExpect(jsonPath("$.data.skillId").value("prs-operations-routing"))
            .andExpect(jsonPath("$.data.recommendedAction").value("review-dispatch"))
            .andExpect(jsonPath("$.data.params.projectId").value("PRJ-001"))
            .andExpect(jsonPath("$.data.context.tenantId").value("tenant-1"));
    }
}
