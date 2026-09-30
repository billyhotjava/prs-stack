package com.yuzhi.prs.common.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * 从网关注入的 X-DTS-* 头装配 CurrentUser。下游服务只信任这些头，不直接验 token。
 * 无头请求放行（holder 为空），由业务接口自行决定是否要求登录。
 */
@Component
@Order(Ordered.HIGHEST_PRECEDENCE + 10)
public class UserContextFilter extends OncePerRequestFilter {

  public static final String H_USER_ID = "X-DTS-User-Id";
  public static final String H_USERNAME = "X-DTS-User-Name";
  public static final String H_NICKNAME = "X-DTS-Display-Name";
  public static final String H_ROLES = "X-DTS-Roles";
  public static final String H_DEPT = "X-DTS-Dept-Code";
  public static final String H_TENANT = "X-DTS-Tenant-Id";

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain chain) throws ServletException, IOException {
    try {
      String userId = request.getHeader(H_USER_ID);
      if (userId != null && !userId.isBlank()) {
        Set<String> roles = split(request.getHeader(H_ROLES));
        UserContextHolder.set(new CurrentUser(
            userId,
            request.getHeader(H_USERNAME),
            request.getHeader(H_NICKNAME),
            roles,
            request.getHeader(H_DEPT),
            request.getHeader(H_TENANT)));
      }
      chain.doFilter(request, response);
    } finally {
      UserContextHolder.clear();
    }
  }

  private static Set<String> split(String csv) {
    if (csv == null || csv.isBlank()) {
      return Set.of();
    }
    return Arrays.stream(csv.split(","))
        .map(String::trim)
        .filter(s -> !s.isEmpty())
        .collect(Collectors.toSet());
  }
}
