package com.yuzhi.prs.audit;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;
import org.junit.jupiter.api.Test;

class AuditEventServiceTest {

    @Test
    void recordsBusinessAuditEventsForPackCriticalActions() {
        AuditEventService service = new AuditEventService();

        service.record(
            "skill.execute",
            "operations-user",
            "skill",
            "prs-feedback-routing",
            Map.of("recommendedAction", "operations-review")
        );

        assertThat(service.listRecent()).hasSize(1);
        AuditEventService.AuditEvent auditEvent = service.listRecent().getFirst();
        assertThat(auditEvent.eventType()).isEqualTo("skill.execute");
        assertThat(auditEvent.actorId()).isEqualTo("operations-user");
        assertThat(auditEvent.objectType()).isEqualTo("skill");
        assertThat(auditEvent.objectId()).isEqualTo("prs-feedback-routing");
        assertThat(auditEvent.details()).containsEntry("recommendedAction", "operations-review");
    }
}
