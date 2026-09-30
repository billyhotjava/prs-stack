package com.yuzhi.prs.auth;

import java.time.Duration;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

/** H5 会话：BFF 登录后签发，存 Redis（key prs:session:{id}，TTL 可配）。 */
@Service
public class SessionService {

  private final StringRedisTemplate redis;
  private final ObjectMapper objectMapper;
  private final Duration ttl;
  private final String prefix;

  public record SessionData(String userId, String username, String nickName, Set<String> roles,
      String deptCode, String tenantId) {
  }

  public SessionService(StringRedisTemplate redis, ObjectMapper objectMapper,
      @Value("${prs.auth.session-ttl:8h}") Duration ttl,
      @Value("${prs.auth.session-prefix:prs:session:}") String prefix) {
    this.redis = redis;
    this.objectMapper = objectMapper;
    this.ttl = ttl;
    this.prefix = prefix;
  }

  public String create(SessionData data) {
    String id = UUID.randomUUID().toString().replace("-", "");
    try {
      redis.opsForValue().set(prefix + id, objectMapper.writeValueAsString(data), ttl);
    } catch (Exception e) {
      throw new IllegalStateException("session persist failed", e);
    }
    return id;
  }

  public Optional<SessionData> validate(String id) {
    if (id == null || id.isBlank()) {
      return Optional.empty();
    }
    String raw = redis.opsForValue().get(prefix + id);
    if (raw == null) {
      return Optional.empty();
    }
    try {
      return Optional.of(objectMapper.readValue(raw, SessionData.class));
    } catch (Exception e) {
      return Optional.empty();
    }
  }

  public void invalidate(String id) {
    if (id != null && !id.isBlank()) {
      redis.delete(prefix + id);
    }
  }
}
