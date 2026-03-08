# PRS Release Checklist

## Before Build

- Confirm `pack/pack-manifest.json` version and runtime URLs are correct.
- Confirm `pack/checksum.sha256` is up to date after any pack file change.
- Confirm backend tests, frontend tests, and frontend build are green.

## Release Build

- Run `bash scripts/release-pack.sh --snapshot` for internal verification.
- Review `dist/artifacts/release-metadata.json` and `dist/artifacts/catalog-entry.json`.
- Verify the ZIP name matches `packId-version`.

## Publish

- Upload the ZIP and release metadata to the target artifact store.
- Attach `pack/CHANGELOG.md` and note required platform version `2.5.0`.
- Record the deployed backend image tag and frontend image tag alongside the pack ZIP.

## After Publish

- Run `bash scripts/validate-pack.sh dist/pack`.
- Confirm `docker compose -f deploy/docker-compose.yml config` still passes.
- Update the go-live record with release time, operator, and rollback owner.
