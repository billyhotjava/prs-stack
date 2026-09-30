package com.yuzhi.prs.shadow;

import com.yuzhi.prs.common.PrsCommonConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

/** 影子服务：身份回显，供 F3/T03 端到端登录验证。无 DB、无 Redis。 */
@SpringBootApplication
@Import(PrsCommonConfig.class)
public class ShadowApplication {

  public static void main(String[] args) {
    SpringApplication.run(ShadowApplication.class, args);
  }
}
