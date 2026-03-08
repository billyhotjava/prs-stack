package com.yuzhi.prs.audit;

import com.yuzhi.prs.security.UserContext;
import java.time.Instant;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuditEventService {

    private final List<AuditEvent> events = new CopyOnWriteArrayList<>();

    public AuditEvent record(
        String eventType,
        String actorId,
        String objectType,
        String objectId,
        Map<String, Object> details
    ) {
        AuditEvent event = new AuditEvent(
            UUID.randomUUID().toString(),
            eventType,
            actorId == null || actorId.isBlank() ? "system" : actorId,
            objectType,
            objectId,
            sanitize(details),
            Instant.now()
        );
        events.add(0, event);
        return event;
    }

    public AuditEvent recordCurrentUser(
        String eventType,
        String objectType,
        String objectId,
        Map<String, Object> details
    ) {
        return record(eventType, currentActorId(), objectType, objectId, details);
    }

    public List<AuditEvent> listRecent() {
        return new ArrayList<>(events);
    }

    private String currentActorId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            return "system";
        }
        Object principal = authentication.getPrincipal();
        if (principal instanceof UserContext userContext) {
            return userContext.userId();
        }
        return authentication.getName() == null || authentication.getName().isBlank()
            ? "system"
            : authentication.getName();
    }

    private Map<String, Object> sanitize(Map<String, Object> details) {
        return details == null ? Map.of() : Map.copyOf(new LinkedHashMap<>(details));
    }

    public record AuditEvent(
        String eventId,
        String eventType,
        String actorId,
        String objectType,
        String objectId,
        Map<String, Object> details,
        Instant occurredAt
    ) {}
}
