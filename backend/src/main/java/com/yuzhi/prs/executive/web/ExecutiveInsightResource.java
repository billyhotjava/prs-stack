package com.yuzhi.prs.executive.web;

import com.yuzhi.prs.executive.service.ExecutiveInsightService;
import com.yuzhi.prs.finance.service.ProjectFinanceFactService;
import com.yuzhi.prs.service.service.FeedbackRoutingService;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/executive/insights")
public class ExecutiveInsightResource {

    private final ExecutiveInsightService executiveInsightService;
    private final ProjectFinanceFactService projectFinanceFactService;
    private final FeedbackRoutingService feedbackRoutingService;

    public ExecutiveInsightResource(
        ExecutiveInsightService executiveInsightService,
        ProjectFinanceFactService projectFinanceFactService,
        FeedbackRoutingService feedbackRoutingService
    ) {
        this.executiveInsightService = executiveInsightService;
        this.projectFinanceFactService = projectFinanceFactService;
        this.feedbackRoutingService = feedbackRoutingService;
    }

    @GetMapping("/portfolio")
    public ExecutiveInsightService.ExecutiveSnapshot getPortfolioSnapshot() {
        return executiveInsightService.buildSnapshot(
            projectFinanceFactService.listAllFacts(),
            List.of(),
            feedbackRoutingService.listQueue().size()
        );
    }
}
