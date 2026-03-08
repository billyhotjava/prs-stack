package com.yuzhi.prs.skill.api;

import java.util.Map;

public record SkillExecuteResponse(
    Map<String, Object> data,
    String summary
) {}
