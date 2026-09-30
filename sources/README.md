# PRS sources（BOM v1.0 重定基，Sprint-1）

版本基线：JDK 25 LTS / Boot 4.1.1 / PG 18 / Valkey 9 / Traefik 3.7 / Keycloak 26.7 /
Liquibase 5（FSL 待法务，不通过换 Flyway）。详见 `../worklog/v1.0.0/sprint-1-202609/assets/版本基线.md`。
无 Nacos/SCA。服务发现用容器 DNS 名；边缘路由 Traefik；鉴权 forwardAuth → prs-auth。

构建要求 JDK 25：
```bash
export JAVA_HOME=/home/billy/.sdkman/candidates/java/25.0.4-tem
export PATH=$JAVA_HOME/bin:$PATH
```

## 模块
| 模块 | 端口 | 职责 |
|---|---|---|
| prs-common | — | R 信封、全局异常、Long 转字符串、X-DTS-* 上下文、OpenAPI。新服务 `@Import(PrsCommonConfig.class)` 即得 |
| prs-platform | 8082 | 基线库拥有者（tenant/dict/org/audit/outbox + prs.project，Liquibase 随启动执行） |
| prs-auth | 8081 | forwardAuth 端点（JWT/JWKS + 会话双模；会话模式 Sprint-2 启用，本期保留已验证代码），只允许内网调用 |
| prs-shadow | 8083 | 身份回显（F3/T03 验证用），无 DB |
| prs-project | 8084 | F7 竖线：prs.project 只读 API（RLS 强制）+ 老库单向同步 |

## 本地构建与运行
```bash
cd sources
mvn -B package                                   # 编译 + 单测（需 JDK 25）
cp deploy/.env.example deploy/.env              # dev 默认值（勿提交真密钥）
docker compose -f deploy/docker-compose.dev.yml up -d --build
```

## 验证（取证命令，F3/T03/T04、F4/T02、F7 用）
```bash
EDGE=http://localhost:38080
# 1) 健康（白名单直通）
curl -s $EDGE/actuator/health
# 2) 无 token 调受保护接口 → 401（负例）
curl -s -o /dev/null -w "%{http_code}\n" $EDGE/api/echo
# 3) 取 token（dev direct grant，alice=项目经理PM，t1 租户）
TOKEN=$(curl -s http://localhost:38082/realms/flower-test/protocol/openid-connect/token \
  -d grant_type=password -d client_id=prs-app \
  -d username=alice -d password=test1234 | python3 -c "import sys,json;print(json.load(sys.stdin)['access_token'])")
# 4) 带 token 调受保护接口 → 200 + 身份回显（roles/tenant）
curl -s $EDGE/api/echo -H "Authorization: Bearer $TOKEN"
# 5) RLS 租户隔离：alice(t1) 只能看到 t1 项目；bob(t2) 只能看到 t2 项目
curl -s "$EDGE/api/prs/projects" -H "Authorization: Bearer $TOKEN"
# 6) 基线库表（Liquibase 已执行）
docker compose -f deploy/docker-compose.dev.yml exec -T prs-pg psql -U prs -d prs -c '\dt'
# 7) 老库同步验证（固件 MySQL，见 deploy/fixtures/mysql-init.sql 起法）
curl -s -X POST http://localhost:38080/api/internal/sync/projects?limit=100
```

## 约定（细则见 F2 规范）
- 响应信封 `R{code,msg,data}`，HTTP 状态与 code 一致；Long 全局转字符串。
- 下游只信任 `X-DTS-Tenant-Id` 等头，不验 token；无头请求由各接口自行决定是否 401。
- 新表一律经 Liquibase changeSet（业务表随各域 Sprint 建）；单号用 DB 序列/Redis，不查最大+1。
- 租户：`tenant_id` bigint NOT NULL + RLS FORCE + fail-closed（无租户 0 行）；单号按（租户,类型,期间）。
- 模板用法见 TEMPLATE.md；Helm chart 见 deploy/helm/prs-service（compose 对照表在 chart README）。
