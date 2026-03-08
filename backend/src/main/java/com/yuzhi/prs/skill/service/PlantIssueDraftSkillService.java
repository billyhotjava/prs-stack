package com.yuzhi.prs.skill.service;

import com.yuzhi.prs.skill.api.SkillExecuteRequest;
import com.yuzhi.prs.skill.api.SkillExecuteResponse;
import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class PlantIssueDraftSkillService {

    public SkillExecuteResponse execute(SkillExecuteRequest request) {
        Map<String, Object> data = new LinkedHashMap<>();
        data.put("skillId", "prs-plant-issue-draft");
        data.put("recommendedAction", "submit-supervisor-review");
        data.put("draftSummary", "AI drafted a plant issue summary for supervisor approval.");
        data.put("params", request.safeParams());
        return new SkillExecuteResponse(data, "Drafted plant issue response for PRS");
    }
}
