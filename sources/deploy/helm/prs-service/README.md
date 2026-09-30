# prs-service chart（R-011 k8s-ready 骨架）

## compose ↔ chart 对照（防漂移，F4/T03 核验用）
| compose 项 | chart 对应 |
|---|---|
| service 镜像 | `image.repository/tag` |
| 端口映射 | Service 80 → `port`（容器内） |
| environment | `env[]`（密钥走 External Secrets，values 不放真值） |
| healthcheck | liveness/readiness 探针（actuator 分离端点） |
| stop_grace_period 35s | `terminationGracePeriodSeconds: 35` + 应用优雅停机 |
| Traefik routers/middlewares | HTTPRoute（Gateway API）+ 网关层中间件（集群侧配） |
| JAVA_TOOL_OPTIONS otel agent | `otel.enabled` 开关 |

## 验证
```bash
helm lint deploy/helm/prs-service
helm template prs-shadow deploy/helm/prs-service --values deploy/helm/prs-service/values.yaml
```
结论记入 `it/baseline.md` 附注（F4/T03）。
