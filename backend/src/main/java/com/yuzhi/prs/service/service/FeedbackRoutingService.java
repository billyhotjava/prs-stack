package com.yuzhi.prs.service.service;

import com.yuzhi.prs.service.domain.CustomerFeedback;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import org.springframework.stereotype.Service;

@Service
public class FeedbackRoutingService {

    private final List<RoutedFeedback> queue = new CopyOnWriteArrayList<>();

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
