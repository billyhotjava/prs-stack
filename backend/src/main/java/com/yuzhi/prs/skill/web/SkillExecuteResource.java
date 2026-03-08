package com.yuzhi.prs.skill.web;

import com.yuzhi.prs.skill.api.SkillExecuteRequest;
import com.yuzhi.prs.skill.api.SkillExecuteResponse;
import com.yuzhi.prs.skill.service.SkillRouterService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/skill")
public class SkillExecuteResource {

    private final SkillRouterService skillRouterService;

    public SkillExecuteResource(SkillRouterService skillRouterService) {
        this.skillRouterService = skillRouterService;
    }

    @PostMapping("/execute")
    public SkillExecuteResponse execute(@RequestBody SkillExecuteRequest request) {
        return skillRouterService.execute(request);
    }
}
