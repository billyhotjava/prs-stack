# PRS

Plants Rental Platform (`PRS`) is an independently developed and independently
deployed industry pack for plant-rental operations.

## Layout

- `backend`: Spring Boot service for business APIs and DTS RPC skill endpoints
- `frontend`: Vite + React remote application
- `pack`: DTS-compatible pack manifest, ontology, menus, and permissions
- `deploy`: standalone deployment assets
- `scripts`: pack build, validation, and smoke scripts

## Commands

- `make help`: show available commands
- `make backend-test`: run backend tests
- `make frontend-build`: build the remote frontend
- `make pack-validate`: validate pack structure
- `make smoke-local`: run local smoke checks
