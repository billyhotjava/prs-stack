package com.yuzhi.prs.skill.service;

import com.yuzhi.prs.skill.api.SkillExecuteRequest;
import com.yuzhi.prs.skill.api.SkillExecuteResponse;
import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class ReconciliationExplainSkillService {

    public SkillExecuteResponse execute(SkillExecuteRequest request) {
        Map<String, Object> data = new LinkedHashMap<>();
        data.put("skillId", "prs-reconciliation-explain");
        data.put("recommendedAction", "finance-review");
        data.put("explanation", "The detected reconciliation difference needs finance confirmation before adjustment.");
        data.put("params", request.safeParams());
        return new SkillExecuteResponse(data, "Prepared reconciliation explanation for PRS");
    }
}
