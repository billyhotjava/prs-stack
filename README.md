# PRS — 花卉租赁 App

> 2026-09-26：以 9 月重写底座和 RDC Sprint-5 的四模块定位为当前演进输入。以下历史原型保留用于能力参考，不代表新基线已验收。

- **重写底座原型**：[sources/README.md](sources/README.md)，由 `/opt/prod/prs/source/dts-prs/sources` 原样导入，包含 common/platform/auth/shadow/project 五个模块。
- **工作记录入口**：[worklog/README.md](worklog/README.md)。导入规划中的旧状态与旧路径按历史快照理解，当前取舍见 [承接说明](worklog/v1.0.0/integration-20260926/README.md)。
- **版本与公共镜像**：[版本承接](worklog/v1.0.0/integration-20260926/version-handoff.md)。保留 9 月 18 日 BOM、9 月 20 日镜像清单的核实日期，不声称是今日全网最新版。
- **原型与复用范围**：[能力对照](worklog/v1.0.0/integration-20260926/prototype-capability-map.md)。既有 React 页面与后端内存实现不直接替代新服务。

本次只导入原型和资料、修订规划；源码字节校验见 [source-manifest.json](worklog/v1.0.0/integration-20260926/source-manifest.json)。未带入 `.env`、编译产物或依赖目录，未执行应用构建、部署或新 BOM 的业务验收。

## 3 月原型（历史参考）

Plants Rental Platform (`PRS`) is an independently developed and independently
deployed industry pack for plant-rental operations.

### Layout

- `backend`: Spring Boot service for business APIs and DTS RPC skill endpoints
- `frontend`: Vite + React remote application
- `pack`: DTS-compatible pack manifest, ontology, menus, and permissions
- `deploy`: standalone deployment assets
- `scripts`: pack build, validation, and smoke scripts

### Commands（仅针对 3 月原型）

- `make help`: show available commands
- `make backend-test`: run backend tests
- `make frontend-build`: build the remote frontend
- `make pack-validate`: validate pack structure
- `make smoke-local`: run local smoke checks

## Shared contracts and module independence

`sources/prs-common` contains PRS-specific framework configuration. It remains
inside PRS; sharing Spring/JPA configuration across all DTS products would couple
their runtimes. Shared Pack wire formats and offline tooling live in the versioned
`dts-common-pack` release. PRS produces its industry assets locally and invokes
that tool through `DTS_PACK_CLI`; no Studio implementation dependency is required.

```bash
DTS_PACK_CLI=/path/to/installed/pack-cli ./tools/build-studio-pack /tmp/new-prs.dtspack
```
