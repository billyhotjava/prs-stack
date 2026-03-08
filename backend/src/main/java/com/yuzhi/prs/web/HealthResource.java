package com.yuzhi.prs.web;

import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/health")
public class HealthResource {

    @GetMapping
    public Map<String, String> getHealth() {
        return Map.of(
            "status", "UP",
            "application", "prs-backend"
        );
    }
}
