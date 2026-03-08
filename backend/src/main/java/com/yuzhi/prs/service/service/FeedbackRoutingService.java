package com.yuzhi.prs.service.service;

import com.yuzhi.prs.audit.AuditEventService;
import com.yuzhi.prs.service.domain.CustomerFeedback;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FeedbackRoutingService {

    private final List<RoutedFeedback> queue = new CopyOnWriteArrayList<>();
    private final AuditEventService auditEventService;

    public FeedbackRoutingService() {
        this(new AuditEventService());
    }

    @Autowired
    public FeedbackRoutingService(AuditEventService auditEventService) {
        this.auditEventService = auditEventService;
    }

    public RoutedFeedback route(CustomerFeedback feedback) {
        String normalizedType = feedback.feedbackType().toLowerCase();
        String priority = switch (normalizedType) {
            case "complaint" -> "high";
            case "additional-request" -> "medium";
            default -> "normal";
        };
        String downstreamIntent = switch (normalizedType) {
            case "complaint" -> "service-recovery";
            case "additional-request" -> "scope-confirmation";
            default -> "customer-response";
        };

        RoutedFeedback routedFeedback = new RoutedFeedback(
            feedback.id(),
            "operations-review",
            priority,
            downstreamIntent,
            feedback
        );
        queue.add(routedFeedback);
        auditEventService.recordCurrentUser(
            "feedback.route",
            "customer-feedback",
            feedback.id(),
            Map.of(
                "queue", routedFeedback.queue(),
                "priority", routedFeedback.priority(),
                "downstreamIntent", routedFeedback.downstreamIntent()
            )
        );
        return routedFeedback;
    }

    public List<RoutedFeedback> listQueue() {
        return new ArrayList<>(queue);
    }

    public record RoutedFeedback(
        String feedbackId,
        String queue,
        String priority,
        String downstreamIntent,
        CustomerFeedback feedback
    ) {}
}
