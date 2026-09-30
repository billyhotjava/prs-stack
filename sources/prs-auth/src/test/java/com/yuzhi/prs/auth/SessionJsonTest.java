package com.yuzhi.prs.auth;

import static org.assertj.core.api.Assertions.assertThat;

import tools.jackson.databind.ObjectMapper;
import com.yuzhi.prs.auth.SessionService.SessionData;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;

@JsonTest
class SessionJsonTest {

  @Autowired
  ObjectMapper objectMapper;

  @Test
  void sessionDataRoundTrip() throws Exception {
    String raw = "{\"userId\":\"u-9\",\"username\":\"carol\",\"nickName\":\"Carol Test\","
        + "\"roles\":[\"FIN\"],\"deptCode\":\"d1\",\"tenantId\":\"t1\"}";
    SessionData data = objectMapper.readValue(raw, SessionData.class);
    assertThat(data.userId()).isEqualTo("u-9");
    assertThat(data.roles()).isEqualTo(Set.of("FIN"));
  }
}
