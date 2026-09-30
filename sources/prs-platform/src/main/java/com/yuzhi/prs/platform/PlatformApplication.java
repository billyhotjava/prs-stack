package com.yuzhi.prs.platform;

import com.yuzhi.prs.common.PrsCommonConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

/** 基线服务：拥有 tenant/dict/org/audit/outbox 表，Liquibase 首版随启动执行。 */
@SpringBootApplication
@Import(PrsCommonConfig.class)
public class PlatformApplication {

  public static void main(String[] args) {
    SpringApplication.run(PlatformApplication.class, args);
  }
}
