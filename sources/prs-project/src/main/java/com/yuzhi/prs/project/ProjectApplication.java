package com.yuzhi.prs.project;

import com.yuzhi.prs.common.PrsCommonConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

/** 项目服务（F7 竖线）：prs.project 只读 API + 老库单向同步。无 ORM（JdbcClient）。 */
@SpringBootApplication
@Import(PrsCommonConfig.class)
public class ProjectApplication {

  public static void main(String[] args) {
    SpringApplication.run(ProjectApplication.class, args);
  }
}
