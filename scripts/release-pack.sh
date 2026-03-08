#!/usr/bin/env bash
set -euo pipefail

script_dir="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
repo_root="$(cd "${script_dir}/.." && pwd)"
dist_root="${repo_root}/dist"
pack_stage_dir="${dist_root}/pack"
artifact_dir="${dist_root}/artifacts"
channel="release"

if [[ "${1:-}" == "--snapshot" ]]; then
  channel="snapshot"
  shift
fi

if [[ $# -ne 0 ]]; then
  printf 'Usage: %s [--snapshot]\n' "${0##*/}" >&2
  exit 1
fi

bash "${script_dir}/build-pack.sh"

manifest_path="${pack_stage_dir}/pack-manifest.json"
manifest_summary="$(
  node - "$manifest_path" <<'NODE'
const fs = require("node:fs");
const manifest = JSON.parse(fs.readFileSync(process.argv[2], "utf8"));
const payload = {
  id: manifest.id,
  name: manifest.name,
  version: manifest.version,
  minPlatformVersion: manifest.minPlatformVersion,
  rpcBaseUrl: manifest.rpcBaseUrl,
  remoteEntryUrl: manifest.frontend?.remoteEntryUrl ?? "",
  exposedModules: manifest.frontend?.exposedModules ?? [],
  sharedDeps: manifest.frontend?.sharedDeps ?? [],
  skills: manifest.skills ?? []
};
console.log(JSON.stringify(payload));
NODE
)"

zip_summary="$(
  node - "${artifact_dir}" "${manifest_summary}" <<'NODE'
const path = require("node:path");
const manifest = JSON.parse(process.argv[3]);
console.log(path.join(process.argv[2], `${manifest.id}-${manifest.version}.zip`));
NODE
)"

release_metadata_path="${artifact_dir}/release-metadata.json"
catalog_seed_path="${artifact_dir}/catalog-entry.json"
generated_at="$(date -u +"%Y-%m-%dT%H:%M:%SZ")"
zip_sha256="$(sha256sum "${zip_summary}" | awk '{print $1}')"

node - "${release_metadata_path}" "${catalog_seed_path}" "${manifest_summary}" "${channel}" "${generated_at}" "${zip_summary}" "${zip_sha256}" <<'NODE'
const fs = require("node:fs");
const path = require("node:path");

const [
  ,
  ,
  releaseMetadataPath,
  catalogSeedPath,
  manifestPayload,
  channel,
  generatedAt,
  zipPath,
  zipSha256
] = process.argv;

const manifest = JSON.parse(manifestPayload);

const releaseMetadata = {
  packId: manifest.id,
  name: manifest.name,
  version: manifest.version,
  channel,
  generatedAt,
  minPlatformVersion: manifest.minPlatformVersion,
  artifact: {
    zipFile: path.basename(zipPath),
    zipSha256
  },
  runtime: {
    rpcBaseUrl: manifest.rpcBaseUrl,
    remoteEntryUrl: manifest.remoteEntryUrl
  },
  frontend: {
    exposedModules: manifest.exposedModules,
    sharedDeps: manifest.sharedDeps
  },
  skills: manifest.skills.map((skill) => ({
    id: skill.id,
    endpoint: skill.endpoint,
    toolType: skill.toolType
  }))
};

const catalogSeed = {
  packId: manifest.id,
  name: manifest.name,
  version: manifest.version,
  status: "READY",
  frontend: {
    remoteEntryUrl: manifest.remoteEntryUrl,
    exposedModules: manifest.exposedModules
  },
  backend: {
    rpcBaseUrl: manifest.rpcBaseUrl,
    skills: manifest.skills.map((skill) => skill.id)
  }
};

fs.writeFileSync(releaseMetadataPath, JSON.stringify(releaseMetadata, null, 2) + "\n");
fs.writeFileSync(catalogSeedPath, JSON.stringify(catalogSeed, null, 2) + "\n");
NODE

printf 'Release metadata written to %s\n' "${release_metadata_path}"
printf 'Catalog seed written to %s\n' "${catalog_seed_path}"
