#!/usr/bin/env bash
set -euo pipefail

script_dir="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
repo_root="$(cd "${script_dir}/.." && pwd)"

printf 'Running PRS backend tests...\n'
(
  cd "${repo_root}"
  ./mvnw -f backend/pom.xml test
)

printf 'Running PRS frontend tests...\n'
pnpm --dir "${repo_root}/frontend" test

printf 'Building PRS frontend remote...\n'
pnpm --dir "${repo_root}/frontend" build

printf 'Validating PRS pack...\n'
bash "${repo_root}/scripts/validate-pack.sh" "${repo_root}/pack"

printf 'Checking Docker Compose configuration...\n'
docker compose -f "${repo_root}/deploy/docker-compose.yml" config >/dev/null

printf 'PRS local smoke checks passed.\n'
