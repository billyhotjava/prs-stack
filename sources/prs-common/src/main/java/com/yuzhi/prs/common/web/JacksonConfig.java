package com.yuzhi.prs.common.web;

import org.springframework.boot.jackson.autoconfigure.JsonMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tools.jackson.databind.module.SimpleModule;
import tools.jackson.databind.ser.std.ToStringSerializer;

/**
 * Long/long 统一序列化为字符串（JS 精度坑：雪花 ID 超过 2^53）。
 * 前端一律按字符串收 ID，后端入参可用 String 或 Long（Jackson 反序列化兼容数字与字符串）。
 * Boot 4 + Jackson 3：经 Module 注册（Builder 已无 serializerByType）。
 */
@Configuration
public class JacksonConfig {

  @Bean
  public JsonMapperBuilderCustomizer longToStringCustomizer() {
    SimpleModule module = new SimpleModule("prs-long-as-string");
    module.addSerializer(Long.class, ToStringSerializer.instance);
    module.addSerializer(Long.TYPE, ToStringSerializer.instance);
    return builder -> builder.addModule(module);
  }
}
