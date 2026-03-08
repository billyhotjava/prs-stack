package com.yuzhi.prs.security;

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
class SecurityConfigIT {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void keepsHealthEndpointPublic() throws Exception {
        mockMvc.perform(get("/api/health"))
            .andExpect(status().isOk());
    }

    @Test
    void requiresAuthenticationForSkillExecution() throws Exception {
        mockMvc.perform(post("/api/skill/execute")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {
                      "skillId": "prs-operations-routing"
                    }
                    """))
            .andExpect(status().isUnauthorized());
    }

    @Test
    void acceptsHeaderBasedUserContextForProtectedApis() throws Exception {
        mockMvc.perform(post("/api/skill/execute")
                .header("X-PRS-User-Id", "user-123")
                .header("X-PRS-Roles", "operations,maintenance")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {
                      "skillId": "prs-operations-routing",
                      "params": {
                        "projectId": "PRJ-001"
                      }
                    }
                    """))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.summary").value("Prepared PRS action plan for skill prs-operations-routing"));
    }
}
