package com.yuzhi.prs.common.web;

import static org.assertj.core.api.Assertions.assertThat;

import com.yuzhi.prs.common.security.CurrentUser;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

class CommonTest {

  @Test
  void envelopeOk() {
    R<String> r = R.ok("x");
    assertThat(r.getCode()).isEqualTo(200);
    assertThat(r.getData()).isEqualTo("x");
  }

  @Test
  void bizExceptionMapsToHttpStatus() {
    GlobalExceptionHandler h = new GlobalExceptionHandler();
    ResponseEntity<R<?>> resp = h.handleBiz(new BizException(400, "bad"));
    assertThat(resp.getStatusCode().value()).isEqualTo(400);
    assertThat(resp.getBody().getMsg()).isEqualTo("bad");
  }

  @Test
  void currentUserRolesImmutable() {
    CurrentUser u = new CurrentUser("1", "a", "A", Set.of("PM"), "d1", "t1");
    assertThat(u.hasRole("PM")).isTrue();
    assertThat(u.hasRole("GM")).isFalse();
  }
}
