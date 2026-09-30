package com.yuzhi.prs.project;

import com.yuzhi.prs.common.web.BizException;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * F7/T02：老库 p_project → PG prs.project 单向同步（影子期方向，R-008）。
 * 老库无租户列，全部归默认租户（映射规则待 F6/T02 确认，见 .env.example 说明）。
 * 独立 DriverManagerDataSource（不同步为 Spring Bean，避免 PG 事务管理器歧义）。
 */
@Service
public class ProjectSyncService {

  private static final String SELECT_SQL = """
      SELECT id, code, name, abbreviation, status, type, customer_type,
             contract_id, manager_id, start_time, end_time, del_flag
        FROM p_project WHERE del_flag = '0' ORDER BY id LIMIT ?""";

  private static final String UPSERT_SQL = """
      INSERT INTO prs.project(id, tenant_id, code, name, abbreviation, status, type,
        customer_type, contract_id, manager_id, start_time, end_time, del_flag, synced_at)
      VALUES (:id, :tenant, :code, :name, :abbr, :status, :type,
        :ctype, :contract, :manager, :start, :end, :del, now())
      ON CONFLICT (id) DO UPDATE SET tenant_id = EXCLUDED.tenant_id, code = EXCLUDED.code,
        name = EXCLUDED.name, abbreviation = EXCLUDED.abbreviation, status = EXCLUDED.status,
        type = EXCLUDED.type, customer_type = EXCLUDED.customer_type,
        contract_id = EXCLUDED.contract_id, manager_id = EXCLUDED.manager_id,
        start_time = EXCLUDED.start_time, end_time = EXCLUDED.end_time,
        del_flag = EXCLUDED.del_flag, synced_at = EXCLUDED.synced_at""";

  private final JdbcClient pg;
  private final String legacyUrl;
  private final String legacyUser;
  private final String legacyPassword;
  private final long defaultTenant;

  public ProjectSyncService(JdbcClient pg,
      @Value("${prs.legacy.url:}") String legacyUrl,
      @Value("${prs.legacy.user:}") String legacyUser,
      @Value("${prs.legacy.password:}") String legacyPassword,
      @Value("${prs.sync.default-tenant:1}") long defaultTenant) {
    this.pg = pg;
    this.legacyUrl = legacyUrl;
    this.legacyUser = legacyUser;
    this.legacyPassword = legacyPassword;
    this.defaultTenant = defaultTenant;
  }

  @Transactional
  public Map<String, Long> sync(int limit) {
    if (legacyUrl == null || legacyUrl.isBlank()) {
      throw new BizException(501, "老库同步未配置（prs.legacy.url 缺失）");
    }
    // 同步事务先锁定租户上下文（RLS WITH CHECK 要求；多租户老库按租户分批，见 F6/T02）
    pg.sql("SELECT set_config('app.tenant_id', :t, true)")
        .param("t", String.valueOf(defaultTenant)).query(String.class).single();
    DriverManagerDataSource lds =
        new DriverManagerDataSource(legacyUrl, legacyUser, legacyPassword);
    lds.setDriverClassName("com.mysql.cj.jdbc.Driver");
    List<Map<String, Object>> rows =
        new JdbcTemplate(lds).queryForList(SELECT_SQL, Math.min(Math.max(limit, 1), 5000));
    long n = 0;
    for (Map<String, Object> r : rows) {
      pg.sql(UPSERT_SQL)
          .param("id", ((Number) r.get("id")).longValue())
          .param("tenant", defaultTenant)
          .param("code", r.get("code"))
          .param("name", r.get("name"))
          .param("abbr", r.get("abbreviation"))
          .param("status", String.valueOf(r.get("status")))
          .param("type", String.valueOf(r.get("type")))
          .param("ctype", r.get("customer_type"))
          .param("contract", r.get("contract_id"))
          .param("manager", r.get("manager_id"))
          .param("start", r.get("start_time"))
          .param("end", r.get("end_time"))
          .param("del", r.get("del_flag"))
          .update();
      n++;
    }
    return Map.of("read", (long) rows.size(), "upserted", n);
  }
}
