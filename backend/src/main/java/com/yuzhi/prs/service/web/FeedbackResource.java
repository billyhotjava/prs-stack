package com.yuzhi.prs.service.web;

import com.yuzhi.prs.service.domain.CustomerFeedback;
import com.yuzhi.prs.service.service.FeedbackRoutingService;
import java.time.Instant;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/customer-feedback")
public class FeedbackResource {

    private final FeedbackRoutingService feedbackRoutingService;

    public FeedbackResource(FeedbackRoutingService feedbackRoutingService) {
        this.feedbackRoutingService = feedbackRoutingService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FeedbackRoutingService.RoutedFeedback create(@RequestBody CreateCustomerFeedbackRequest request) {
        CustomerFeedback feedback = new CustomerFeedback(
            request.feedbackId(),
            request.customerId(),
            request.projectId(),
            request.feedbackType(),
            request.title(),
            request.message(),
            request.requestedBy(),
            Instant.parse(request.submittedAt())
        );
        return feedbackRoutingService.route(feedback);
    }

    @GetMapping("/queue")
    public List<FeedbackRoutingService.RoutedFeedback> listQueue() {
        return feedbackRoutingService.listQueue();
    }

    public record CreateCustomerFeedbackRequest(
        String feedbackId,
        String customerId,
        String projectId,
        String feedbackType,
        String title,
        String message,
        String requestedBy,
        String submittedAt
    ) {}
}
