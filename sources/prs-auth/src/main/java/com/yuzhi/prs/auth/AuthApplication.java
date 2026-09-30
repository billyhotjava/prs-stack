package com.yuzhi.prs.auth;

import com.yuzhi.prs.common.PrsCommonConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

/** 鉴权服务：Traefik forwardAuth 端点（JWT/会话双模），只允许内网调用。 */
@SpringBootApplication
@Import(PrsCommonConfig.class)
public class AuthApplication {

  public static void main(String[] args) {
    SpringApplication.run(AuthApplication.class, args);
  }
}
