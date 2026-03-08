package com.yuzhi.prs.service.web;

import com.yuzhi.prs.service.domain.ServiceFeedEntry;
import com.yuzhi.prs.service.service.ServiceFeedService;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/service-feed")
public class ServiceFeedResource {

    private final ServiceFeedService serviceFeedService;

    public ServiceFeedResource(ServiceFeedService serviceFeedService) {
        this.serviceFeedService = serviceFeedService;
    }

    @GetMapping("/projects/{projectId}")
    public List<ServiceFeedEntry> listProjectFeed(@PathVariable String projectId) {
        return serviceFeedService.listProjectFeed(projectId);
    }
}
