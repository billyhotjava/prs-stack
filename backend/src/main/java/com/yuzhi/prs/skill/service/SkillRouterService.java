package com.yuzhi.prs.skill.service;

import com.yuzhi.prs.skill.api.SkillExecuteRequest;
import com.yuzhi.prs.skill.api.SkillExecuteResponse;
import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class SkillRouterService {

    public SkillExecuteResponse execute(SkillExecuteRequest request) {
        String skillId = request.skillId() == null || request.skillId().isBlank()
            ? "unknown-skill"
            : request.skillId();

        Map<String, Object> responseData = new LinkedHashMap<>();
        responseData.put("skillId", skillId);
        responseData.put("recommendedAction", "review-dispatch");
        responseData.put("params", request.safeParams());
        responseData.put("context", request.safeContext());

        return new SkillExecuteResponse(
            responseData,
            "Prepared PRS action plan for skill " + skillId
        );
    }
}
