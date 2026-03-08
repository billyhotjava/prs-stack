SHELL := /usr/bin/env bash

.PHONY: help backend-test frontend-build pack-validate smoke-local

help:
	@printf "PRS commands\n"
	@printf "  make backend-test   Run backend tests\n"
	@printf "  make frontend-build Build remote frontend\n"
	@printf "  make pack-validate  Validate pack structure\n"
	@printf "  make smoke-local    Run local smoke checks\n"

backend-test:
	./mvnw -f backend/pom.xml test

frontend-build:
	pnpm --dir frontend build

pack-validate:
	bash scripts/validate-pack.sh pack

smoke-local:
	bash scripts/smoke-local.sh
