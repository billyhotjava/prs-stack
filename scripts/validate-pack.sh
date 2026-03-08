#!/usr/bin/env bash
set -euo pipefail

if [[ $# -ne 1 ]]; then
  printf 'Usage: %s <pack-dir>\n' "${0##*/}" >&2
  exit 1
fi

pack_dir="$(cd "$1" && pwd)"
current_platform="${PRS_PLATFORM_VERSION:-2.5.0}"

required_files=(
  "pack-manifest.json"
  "ontology/apppack.json"
  "config/menus.json"
  "config/permissions.json"
  "checksum.sha256"
)

for relative_path in "${required_files[@]}"; do
  if [[ ! -f "${pack_dir}/${relative_path}" ]]; then
    printf 'Missing required file: %s\n' "${relative_path}" >&2
    exit 1
  fi
done

manifest_path="${pack_dir}/pack-manifest.json"
manifest_summary="$(
  node - "$manifest_path" <<'NODE'
const fs = require("node:fs");

const manifestPath = process.argv[2];
const manifest = JSON.parse(fs.readFileSync(manifestPath, "utf8"));

for (const key of ["id", "name", "version", "minPlatformVersion", "vendor", "description", "menus", "permissions"]) {
  if (typeof manifest[key] !== "string" || manifest[key].trim() === "") {
    throw new Error(`Manifest field "${key}" must be a non-empty string`);
  }
}

if (!Array.isArray(manifest.skills) || manifest.skills.length === 0) {
  throw new Error('Manifest "skills" must contain at least one entry');
}

if (!manifest.frontend || typeof manifest.frontend !== "object") {
  throw new Error('Manifest "frontend" section is required');
}

if (typeof manifest.frontend.remoteEntryUrl !== "string" || manifest.frontend.remoteEntryUrl.trim() === "") {
  throw new Error('Manifest "frontend.remoteEntryUrl" must be a non-empty string');
}

if (!Array.isArray(manifest.frontend.exposedModules) || manifest.frontend.exposedModules.length === 0) {
  throw new Error('Manifest "frontend.exposedModules" must contain at least one module');
}

if (!manifest.ontology || typeof manifest.ontology.objectTypes !== "string" || manifest.ontology.objectTypes.trim() === "") {
  throw new Error('Manifest "ontology.objectTypes" must be a non-empty string');
}

for (const skill of manifest.skills) {
  if (typeof skill.id !== "string" || typeof skill.toolType !== "string" || typeof skill.endpoint !== "string") {
    throw new Error("Every skill must define id, toolType, and endpoint");
  }
}

console.log(`${manifest.id}\t${manifest.version}\t${manifest.minPlatformVersion}\t${manifest.ontology.objectTypes}\t${manifest.menus}\t${manifest.permissions}`);
NODE
)"

IFS=$'\t' read -r pack_id pack_version min_platform ontology_file menus_file permissions_file <<<"${manifest_summary}"

if [[ "$(printf '%s\n%s\n' "${min_platform}" "${current_platform}" | sort -V | head -n 1)" != "${min_platform}" ]]; then
  printf 'Pack requires platform %s but current platform is %s\n' "${min_platform}" "${current_platform}" >&2
  exit 1
fi

for referenced_file in "${ontology_file}" "${menus_file}" "${permissions_file}"; do
  if [[ ! -f "${pack_dir}/${referenced_file}" ]]; then
    printf 'Manifest reference does not exist: %s\n' "${referenced_file}" >&2
    exit 1
  fi
done

json_files=(
  "${pack_dir}/pack-manifest.json"
  "${pack_dir}/${ontology_file}"
  "${pack_dir}/${menus_file}"
  "${pack_dir}/${permissions_file}"
)

for json_file in "${json_files[@]}"; do
  node -e 'JSON.parse(require("node:fs").readFileSync(process.argv[1], "utf8"));' "${json_file}" >/dev/null
done

actual_checksum_file="$(mktemp)"
trap 'rm -f "${actual_checksum_file}"' EXIT

(
  cd "${pack_dir}"
  while IFS= read -r relative_path; do
    sha256sum "${relative_path}"
  done < <(find . -type f ! -name checksum.sha256 -printf '%P\n' | sort)
) > "${actual_checksum_file}"

if ! cmp -s "${pack_dir}/checksum.sha256" "${actual_checksum_file}"; then
  printf 'Checksum verification failed for %s\n' "${pack_dir}" >&2
  diff -u "${pack_dir}/checksum.sha256" "${actual_checksum_file}" || true
  exit 1
fi

printf '✓ pack-manifest.json found\n'
printf '✓ Schema baseline valid (pack-manifest-v1)\n'
printf '✓ minPlatformVersion %s compatible with current %s\n' "${min_platform}" "${current_platform}"
printf '✓ Checksum verified\n'
printf '✓ Pack "%s" v%s is valid\n' "${pack_id}" "${pack_version}"
