# TEMPLATE — 新服务接入模板（prs-shadow 为范本）

## 新建一个服务（如 prs-biz）
1. 复制 `prs-shadow/` 为新目录，改名包与类（`com.yuzhi.prs.shadow` → `com.yuzhi.prs.biz`）。
2. parent `pom.xml` 加 `<module>`；新服务 pom 沿用 shadow 依赖，按需加：
   JPA+Liquibase+PG（要建表时，Liquibase change-log 指向自有目录）、Redis（要会话/缓存时）。
3. 启动类加 `@Import(PrsCommonConfig.class)`（R 信封/异常/Long 转字符串/上下文/OpenAPI 全得）。
4. 需要鉴权的接口：从 `UserContextHolder.get()` 取身份，为空抛 `BizException(401, "未登录")`；
   不要在服务里验 token（网关 forwardAuth 已做）。
5. Dockerfile 复制 shadow 的改模块名；`deploy/traefik/dynamic/prs.yml` 加 router
   （受保护路径挂 `prs-forward-auth` 中间件；公开路径直通）。
6. 需要新表：在自有 `db/changelog/` 加 changeSet（禁止 @Table 手动建表、禁止跨服务直连他库表）。

## 默认即得、不许绕过
tenant 字段（新表必备，DataScope 二期强制）、审计字段（create_by/time）、
幂等键（写接口必备，Sprint-2 规范细化）、outbox（领域事件经 outbox 表，Kafka 接入后由 relay 投递）。

## 本模板验证状态
F2/T03：prs-shadow 即模板实例，本地启动 + 默认能力（401/信封/文档）已验证（见任务实际结果）。
