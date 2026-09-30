package com.yuzhi.prs.auth;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.oauth2.core.DelegatingOAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtTimestampValidator;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;

/**
 * 显式安全链（覆盖 Boot 默认全站 JWT 链，否则 forward 端点自身被锁死，会话模式永不可达）。
 * forward 端点 permitAll：真正的门是 Traefik（该端点只允许内网调用，生产不映射宿主端口）。
 */
@Configuration
public class SecurityConfig {

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
        .csrf(AbstractHttpConfigurer::disable)
        .authorizeHttpRequests(auth -> auth
            .requestMatchers("/api/internal/auth/forward",
                "/actuator/health", "/actuator/info").permitAll()
            .anyRequest().authenticated())
        .oauth2ResourceServer(oauth2 -> oauth2.jwt(jwt -> {
        }));
    return http.build();
  }


  @Bean
  public JwtDecoder jwtDecoder(
      @Value("${prs.auth.issuer-uri}") String issuerUri,
      @Value("${prs.auth.trusted-issuers:}") String trustedIssuers,
      @Value("${prs.auth.audience:prs-app}") String audience) {
    // JWKS 直取标准 certs 路径（同 realm 密钥与 host 无关）；iss 校验走可信列表。
    NimbusJwtDecoder decoder = NimbusJwtDecoder
        .withJwkSetUri(issuerUri + "/protocol/openid-connect/certs").build();
    List<String> issuers = new ArrayList<>();
    issuers.add(issuerUri);
    for (String s : trustedIssuers.split(",")) {
      if (!s.isBlank()) {
        issuers.add(s.trim());
      }
    }
    OAuth2TokenValidator<Jwt> validators = new DelegatingOAuth2TokenValidator<>(
        new IssuerListValidator(issuers), new AudienceValidator(audience),
        new JwtTimestampValidator());
    decoder.setJwtValidator(validators);
    return decoder;
  }

  /** iss 必须在可信列表内（生产单 canonical；dev 允许内外双 URL，见 T05）。 */
  static class IssuerListValidator implements OAuth2TokenValidator<Jwt> {

    private final List<String> issuers;

    IssuerListValidator(List<String> issuers) {
      this.issuers = List.copyOf(issuers);
    }

    @Override
    public org.springframework.security.oauth2.core.OAuth2TokenValidatorResult validate(Jwt token) {
      if (issuers.contains(token.getClaimAsString("iss"))) {
        return org.springframework.security.oauth2.core.OAuth2TokenValidatorResult.success();
      }
      return org.springframework.security.oauth2.core.OAuth2TokenValidatorResult.failure(
          new org.springframework.security.oauth2.core.OAuth2Error("invalid_issuer",
              "iss " + token.getClaimAsString("iss") + " not in " + issuers, null));
    }
  }

  /**
   * aud 允许字符串或数组包含任一配置值；Keycloak code-flow token 常无 aud，
   * 此时退化校验 azp（token 签发对象，同生态网关的标准做法，见 T05）。
   */
  static class AudienceValidator implements OAuth2TokenValidator<Jwt> {

    private final List<String> allowed;

    AudienceValidator(String audienceCsv) {
      this.allowed = List.of(audienceCsv.split(",")).stream().map(String::trim).toList();
    }

    @Override
    public org.springframework.security.oauth2.core.OAuth2TokenValidatorResult validate(Jwt token) {
      List<String> aud = token.getAudience();
      boolean audOk = aud != null && aud.stream().anyMatch(allowed::contains);
      boolean azpOk = allowed.contains(token.getClaimAsString("azp"));
      if (audOk || azpOk) {
        return org.springframework.security.oauth2.core.OAuth2TokenValidatorResult.success();
      }
      return org.springframework.security.oauth2.core.OAuth2TokenValidatorResult.failure(
          new org.springframework.security.oauth2.core.OAuth2Error("invalid_audience",
              "aud " + aud + " azp " + token.getClaimAsString("azp")
                  + " not in " + allowed, null));
    }
  }
}
