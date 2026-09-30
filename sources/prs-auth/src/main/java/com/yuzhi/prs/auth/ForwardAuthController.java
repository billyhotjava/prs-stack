package com.yuzhi.prs.auth;

import com.yuzhi.prs.common.security.UserContextFilter;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Traefik forwardAuth 端点。Traefik 把原始请求的 Authorization/Cookie 原样转发到此；
 * 本端点返回 2xx + X-DTS-* 响应头（Traefik 按 authResponseHeaders 回注为下游请求头），
 * 否则 401。下游服务只信任头，不验 token。
 *
 * 双模：① Authorization: Bearer JWT（PC/API）；② Cookie PRS_SESSION（H5/BFF 会话）。
 */
@RestController
@RequestMapping("/api/internal/auth")
public class ForwardAuthController {

  private static final Set<String> IGNORED_ROLES = Set.of(
      "default-roles-flower-test", "default-roles-flower",
      "offline_access", "uma_authorization");

  private static final Logger log = LoggerFactory.getLogger(ForwardAuthController.class);

  private final JwtDecoder jwtDecoder;
  private final SessionService sessions;

  public ForwardAuthController(JwtDecoder jwtDecoder, SessionService sessions) {
    this.jwtDecoder = jwtDecoder;
    this.sessions = sessions;
  }

  @GetMapping("/forward")
  public ResponseEntity<?> forward(HttpServletRequest request) {
    return resolve(request)
        .map(id -> ResponseEntity.ok()
            .header(UserContextFilter.H_USER_ID, id.userId())
            .header(UserContextFilter.H_USERNAME, orEmpty(id.username()))
            .header(UserContextFilter.H_NICKNAME, orEmpty(id.nickName()))
            .header(UserContextFilter.H_ROLES, String.join(",", id.roles()))
            .header(UserContextFilter.H_DEPT, orEmpty(id.deptCode()))
            .header(UserContextFilter.H_TENANT, orEmpty(id.tenantId()))
            .build())
        .orElseGet(() -> ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
  }

  private Optional<Identity> resolve(HttpServletRequest request) {
    String auth = request.getHeader("Authorization");
    if (auth != null && auth.startsWith("Bearer ")) {
      Optional<Identity> fromJwt = fromJwt(auth.substring(7).trim());
      if (fromJwt.isPresent()) {
        return fromJwt;
      }
      log.debug("forward auth: bearer token rejected");
    }
    Cookie[] cookies = request.getCookies();
    log.debug("forward auth: cookies present={}",
        cookies == null ? 0 : cookies.length);
    if (cookies != null) {
      for (Cookie c : cookies) {
        if ("PRS_SESSION".equals(c.getName())) {
          Optional<Identity> fromSession = fromSession(c.getValue());
          if (fromSession.isPresent()) {
            return fromSession;
          }
          log.debug("forward auth: session invalid");
        }
      }
    }
    return Optional.empty();
  }

  private Optional<Identity> fromJwt(String token) {
    final Jwt jwt;
    try {
      jwt = jwtDecoder.decode(token);
    } catch (Exception e) {
      return Optional.empty();
    }
    String userId = jwt.getSubject();
    if (userId == null || userId.isBlank()) {
      return Optional.empty();
    }
    Set<String> roles = new HashSet<>();
    Object realmAccess = jwt.getClaim("realm_access");
    if (realmAccess instanceof Map<?, ?> ra) {
      Object r = ra.get("roles");
      if (r instanceof Collection<?> c) {
        c.forEach(x -> roles.add(String.valueOf(x)));
      }
    }
    roles.removeAll(IGNORED_ROLES);
    String tenant = null;
    Object groups = jwt.getClaim("groups");
    if (groups instanceof Collection<?> c) {
      for (Object g : c) {
        String s = String.valueOf(g);
        String t = tenantOf(s);
        if (t != null) {
          tenant = t;
          break;
        }
      }
    }
    return Optional.of(new Identity(
        userId,
        jwt.getClaimAsString("preferred_username"),
        jwt.getClaimAsString("name"),
        roles,
        null,
        tenant == null ? "default" : tenant));
  }

  /** groups 形如 /tenants/{id}[...] 取首段；兼容无前导斜杠。 */
  static String tenantOf(String group) {
    if (group == null) {
      return null;
    }
    String s = group.startsWith("/") ? group.substring(1) : group;
    String[] parts = s.split("/");
    if (parts.length >= 2 && "tenants".equals(parts[0]) && !parts[1].isBlank()) {
      return parts[1];
    }
    return null;
  }

  private Optional<Identity> fromSession(String sessionId) {
    return sessions.validate(sessionId)
        .map(d -> new Identity(d.userId(), d.username(), d.nickName(), d.roles(),
            d.deptCode(), d.tenantId() == null ? "default" : d.tenantId()));
  }

  private static String orEmpty(String s) {
    return s == null ? "" : s;
  }

  private record Identity(String userId, String username, String nickName, Set<String> roles,
      String deptCode, String tenantId) {
  }
}
