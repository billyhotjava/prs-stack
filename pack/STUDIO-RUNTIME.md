# PRS Studio assets

`studio/` is the data-only `dts.pack/v1` source for `prs-flower@0.1.2`, requiring the
new Studio 1.0.0 runtime. It contains 31 assets: five effective ontologies, 19 governance
and evaluation documents, one response catalog, four prompts, and one collection of
57 final query templates, plus the service-referenced draft action. The templates were exported by replaying 19 original Liquibase
changesets, including runtime/dataset fixes 031 and 032.

```bash
DTS_PACK_CLI=/path/to/installed/dts-common/tools/pack-cli \
  ./tools/build-studio-pack /new/path/prs-flower-0.1.2.dtspack
```

The wrapper validates committed hashes and builds a deterministic archive. It does not
install/activate it or include deployment credentials. Logical sources `prs-mart` and
`prs-app` still need the governed Stack query binding.

`pack-manifest.json` remains the existing PRS RPC/UI hosting contract. It is not renamed,
reinterpreted, or bundled into the Studio archive. Frontend/RPC protocol convergence,
verified delegated user credentials and declarative tool migration remain separate planned work.
Deployment action bindings are illustrated in `studio-action-bindings.example.yaml`; no
credentials or destination base URL are stored in the data-only archive.

The old `field-operations.json` declared the same domain as `flowerbiz.json` and was
shadowed by the latter. Only the historically effective ontology is included. Original
input remains in Studio's migration history and transitional resources.

Version 0.1.2 adds explicit `match_order` values for all 57 templates. SQL, parameters,
question patterns, priorities and active flags are unchanged. The order is captured after
replaying both historical template changes and the legacy ownership handover, then checked
against the previously saved 106-question baseline. Studio applies priority first and the
Pack-declared order second; numeric database IDs and physical row order are not Pack contracts.

Fresh bootstrap uses Studio's `studio-pack` profile and requires the implementation containing
the `v1.1.0-004-template-match-order` migration. Use 0.1.2 for this lane; 0.1.1 is retained as
the historical runtime checkpoint. These source versions are development checkpoints, not
production image/release acceptance.

The Pack CLI is the versioned Common release artifact. It does not require a running
Studio process or a Studio source checkout. `STUDIO_PACK_CLI` remains a compatibility
alias; new producers should set `DTS_PACK_CLI`.
