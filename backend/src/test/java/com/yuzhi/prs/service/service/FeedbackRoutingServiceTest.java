package com.yuzhi.prs.service.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.yuzhi.prs.service.domain.CustomerFeedback;
import java.time.Instant;
import org.junit.jupiter.api.Test;

class FeedbackRoutingServiceTest {

    @Test
    void routesComplaintsAndRequestsIntoOperationsReviewQueue() {
        FeedbackRoutingService service = new FeedbackRoutingService();

        FeedbackRoutingService.RoutedFeedback routedFeedback = service.route(new CustomerFeedback(
            "FDB-001",
            "CUS-001",
            "PRJ-001",
            "complaint",
            "Lobby ficus leaf browning",
            "Client reported browning after the last maintenance visit",
            "Ms. Lin",
            Instant.parse("2026-03-08T08:30:00Z")
        ));

        assertThat(routedFeedback.queue()).isEqualTo("operations-review");
        assertThat(routedFeedback.priority()).isEqualTo("high");
        assertThat(routedFeedback.downstreamIntent()).isEqualTo("service-recovery");
        assertThat(service.listQueue())
            .singleElement()
            .extracting(FeedbackRoutingService.RoutedFeedback::feedbackId)
            .isEqualTo("FDB-001");
    }
}
