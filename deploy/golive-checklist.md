# PRS Go-Live Checklist

## Readiness

- Backend image, frontend image, and pack ZIP all match the approved release metadata.
- `bash scripts/smoke-local.sh` passed on the release candidate commit.
- Customer portal routes, field workbench routes, finance routes, and operating cockpit routes all render from the built remote.

## Data

- Seed roles and initial projects are loaded or queued for import.
- Current contracts, projects, and active plant assets are confirmed in the target environment.
- Finance opening balances and reconciliation exceptions are reviewed by finance before cutover.

## Operations

- On-call owners are assigned for backend, frontend, field operations, and finance support.
- Rollback owner and archive location are recorded.
- Release metadata, catalog seed, and pack ZIP are copied to the deployment ticket.

## Cutover

- Deploy backend and frontend images.
- Register the new pack catalog entry and validate remote loading.
- Confirm `/actuator/health`, project APIs, finance APIs, and skill APIs respond after cutover.
