package com.yuzhi.prs.common.security;

import java.util.Set;

/** 当前登录人（由网关/forwardAuth 注入 X-DTS-* 头，UserContextFilter 装配）。 */
public record CurrentUser(
    String userId,
    String username,
    String nickName,
    Set<String> roles,
    String deptCode,
    String tenantId) {

  public CurrentUser {
    roles = roles == null ? Set.of() : Set.copyOf(roles);
  }

  public boolean hasRole(String role) {
    return roles.contains(role);
  }
}
