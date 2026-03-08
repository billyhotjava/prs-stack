#!/usr/bin/env bash
set -euo pipefail

if command -v mvn >/dev/null 2>&1; then
  exec mvn "$@"
fi

printf 'mvn is required but was not found in PATH\n' >&2
exit 1
