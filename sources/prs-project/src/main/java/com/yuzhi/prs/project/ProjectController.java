package com.yuzhi.prs.project;

import com.yuzhi.prs.common.security.UserContextHolder;
import com.yuzhi.prs.common.web.BizException;
import com.yuzhi.prs.common.web.R;
import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * F7/T01：项目只读 API。租户隔离由 PG RLS FORCE 强制——SQL 文本无 tenant 谓词；
 * 事务内 SET LOCAL app.tenant_id；tenant 非法直接 400（fail-closed）。
 */
@RestController
@RequestMapping("/api/prs")
public class ProjectController {

  private final JdbcClient jdbc;

  public ProjectController(JdbcClient jdbc) {
    this.jdbc = jdbc;
  }

  @GetMapping("/projects")
  @Transactional(readOnly = true)
  public R<Map<String, Object>> list(
      @RequestParam(defaultValue = "") String status,
      @RequestParam(defaultValue = "") String type,
      @RequestParam(defaultValue = "1") int page,
      @RequestParam(defaultValue = "20") int size) {
    var user = UserContextHolder.get();
    if (user == null || user.tenantId() == null) {
      throw new BizException(401, "未登录");
    }
    final long tenant;
    try {
      tenant = Long.parseLong(user.tenantId());
    } catch (NumberFormatException e) {
      throw new BizException(400, "租户非法");
    }
    jdbc.sql("SELECT set_config('app.tenant_id', :t, true)")
        .param("t", String.valueOf(tenant)).query(String.class).single();
    int limit = Math.min(Math.max(size, 1), 100);
    int offset = Math.max(page - 1, 0) * limit;
    List<Map<String, Object>> items = jdbc.sql("""
        SELECT id, code, name, status, type, manager_id AS "managerId",
               count(*) OVER () AS total
          FROM prs.project
         WHERE (:status = '' OR status = :status)
           AND (:type = '' OR type = :type)
         ORDER BY id LIMIT :limit OFFSET :offset
        """)
        .param("status", status)
        .param("type", type)
        .param("limit", limit)
        .param("offset", offset)
        .query().listOfRows();
    long total = items.isEmpty() ? 0L : ((Number) items.get(0).get("total")).longValue();
    return R.ok(Map.of("items", items, "total", total));
  }
}
