# PRS Studio assets

`studio/` is the data-only `dts.pack/v1` source for `prs-flower@0.1.1`, requiring the
new Studio 1.0.0 runtime. It contains 31 assets: five effective ontologies, 19 governance
and evaluation documents, one response catalog, four prompts, and one collection of
57 final query templates, plus the service-referenced draft action. The templates were exported by replaying 19 original Liquibase
changesets, including runtime/dataset fixes 031 and 032.

```bash
STUDIO_PACK_CLI=/data/dts-studio/engine/tools/pack-cli \
  ./tools/build-studio-pack /new/path/prs-flower-0.1.1.dtspack
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
