package com.yuzhi.prs.project;

import com.yuzhi.prs.common.web.BizException;
import com.yuzhi.prs.common.web.R;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 同步触发口（集群内调用，Traefik 配无鉴权路由；生产靠 NetworkPolicy 限制）。
 * 老库未配置时 501（部署时按 .env.example 配只读账号）。
 */
@RestController
@RequestMapping("/api/internal/sync")
public class SyncController {

  private final ProjectSyncService syncService;

  public SyncController(@Autowired(required = false) ProjectSyncService syncService) {
    this.syncService = syncService;
  }

  @PostMapping("/projects")
  public R<Map<String, Long>> syncProjects(@RequestParam(defaultValue = "500") int limit) {
    if (syncService == null) {
      throw new BizException(501, "老库同步未配置（prs.legacy.url 缺失）");
    }
    return R.ok(syncService.sync(limit));
  }
}
