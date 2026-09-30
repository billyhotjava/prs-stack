package com.yuzhi.prs.common;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * 新服务接入：在启动类加 {@code @Import(PrsCommonConfig.class)}，
 * 即得 R 信封、全局异常、Long 转字符串、X-DTS-* 上下文、OpenAPI。
 */
@Configuration
@ComponentScan(basePackages = "com.yuzhi.prs.common")
public class PrsCommonConfig {
}
