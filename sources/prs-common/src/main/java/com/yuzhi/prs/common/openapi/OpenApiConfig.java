package com.yuzhi.prs.common.openapi;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/** OpenAPI 默认信息（各服务可用 prs.openapi.title 覆盖）。 */
@Configuration
public class OpenApiConfig {

  @Bean
  public OpenAPI prsOpenApi(
      @Value("${prs.openapi.title:PRS API}") String title,
      @Value("${prs.openapi.version:1.0.0-SNAPSHOT}") String version) {
    return new OpenAPI().info(new Info().title(title).version(version));
  }
}
