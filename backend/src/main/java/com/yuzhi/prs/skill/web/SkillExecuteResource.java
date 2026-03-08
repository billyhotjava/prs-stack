package com.yuzhi.prs.skill.web;

import com.yuzhi.prs.audit.AuditEventService;
import com.yuzhi.prs.skill.api.SkillExecuteRequest;
import com.yuzhi.prs.skill.api.SkillExecuteResponse;
import com.yuzhi.prs.skill.service.SkillRouterService;
import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/skill")
public class SkillExecuteResource {

    private final SkillRouterService skillRouterService;
    private final AuditEventService auditEventService;

    public SkillExecuteResource(
        SkillRouterService skillRouterService,
        AuditEventService auditEventService
    ) {
        this.skillRouterService = skillRouterService;
        this.auditEventService = auditEventService;
    }

    @PostMapping("/execute")
    public SkillExecuteResponse execute(@RequestBody SkillExecuteRequest request) {
        SkillExecuteResponse response = skillRouterService.execute(request);
        Map<String, Object> details = new LinkedHashMap<>();
        details.put("recommendedAction", response.data().get("recommendedAction"));
        details.put("skillId", request.skillId());
        auditEventService.recordCurrentUser("skill.execute", "skill", request.skillId(), details);
        return response;
    }
}
