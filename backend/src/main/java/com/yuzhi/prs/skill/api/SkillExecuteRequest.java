package com.yuzhi.prs.skill.api;

import java.util.Map;

public record SkillExecuteRequest(
    String skillId,
    Map<String, Object> params,
    Map<String, Object> context
) {

    public Map<String, Object> safeParams() {
        return params == null ? Map.of() : params;
    }

    public Map<String, Object> safeContext() {
        return context == null ? Map.of() : context;
    }
}
