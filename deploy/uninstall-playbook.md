# PRS Uninstall Playbook

## Scope

Use this only when removing PRS from an environment entirely.

## Steps

1. Disable new user access and stop scheduled PRS jobs.
2. Export the final pack ZIP, release metadata, and finance reconciliation outputs for archive.
3. Remove PRS catalog registration from the host runtime.
4. Stop and remove `prs-backend` and `prs-frontend` containers.
5. Archive the latest database snapshot and uploaded field media.
6. Remove DNS or reverse-proxy routes that expose PRS endpoints.

## Safeguards

- Keep archived pack ZIP, metadata, and database snapshot together.
- Keep customer-visible service history available before removing the live system.
- Record uninstall reason, approver, operator, and archive location.
