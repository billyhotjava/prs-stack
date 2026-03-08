# PRS Upgrade Playbook

## Scope

Use this playbook when replacing one PRS pack build with another in the same DTS-compatible environment.

## Steps

1. Build the candidate with `bash scripts/release-pack.sh --snapshot`.
2. Validate the unpacked pack with `bash scripts/validate-pack.sh dist/pack`.
3. Compare `dist/artifacts/release-metadata.json` against the currently deployed release.
4. Deploy the matching `prs-backend` and `prs-frontend` images.
5. Register the new pack ZIP and catalog entry in the target runtime.
6. Run `bash scripts/smoke-local.sh` or the environment-specific smoke suite.

## Required Checks

- Manifest version increased or was explicitly approved for redeploy.
- `minPlatformVersion` remains compatible with the target DTS runtime.
- RPC base URL and remote entry URL point at the new deployment endpoints.
- No open reconciliation or field-task data migrations are pending.

## Rollback Trigger

Rollback immediately if pack validation fails, the remote entry cannot be loaded, or key APIs fail during smoke checks.
