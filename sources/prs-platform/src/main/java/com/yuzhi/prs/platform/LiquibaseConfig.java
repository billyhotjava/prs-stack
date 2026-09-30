package com.yuzhi.prs.platform;

import javax.sql.DataSource;
import liquibase.integration.spring.SpringLiquibase;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Boot 4.1 已移除 Liquibase 自动配置（FSL 许可证风波连锁），此处手动装配。
 * 注意：未来 JPA 实体出现后，需保证 EntityManagerFactory 在此之后初始化
 * （Sprint-2 加 depends-on 接线）；法务若否决 Liquibase 5，changelog 可转 Flyway SQL
 *（Boot 4.1 仍保留 Flyway 自动配置）。
 */
@Configuration
public class LiquibaseConfig {

  @Bean
  public SpringLiquibase liquibase(DataSource dataSource,
      @Value("${spring.liquibase.change-log:classpath:db/changelog/db.changelog-master.xml}")
      String changeLog) {
    SpringLiquibase liquibase = new SpringLiquibase();
    liquibase.setDataSource(dataSource);
    liquibase.setChangeLog(changeLog);
    return liquibase;
  }
}
