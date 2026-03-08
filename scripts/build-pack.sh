#!/usr/bin/env bash
set -euo pipefail

script_dir="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
repo_root="$(cd "${script_dir}/.." && pwd)"
pack_dir="${repo_root}/pack"
dist_dir="${repo_root}/dist/pack"
stage_dir="${dist_dir}/package"

if ! command -v zip >/dev/null 2>&1; then
  printf 'The "zip" command is required to build the PRS pack.\n' >&2
  exit 1
fi

manifest_path="${pack_dir}/pack-manifest.json"
manifest_summary="$(
  node - "$manifest_path" <<'NODE'
const fs = require("node:fs");
const manifest = JSON.parse(fs.readFileSync(process.argv[2], "utf8"));
console.log(`${manifest.id}\t${manifest.version}`);
NODE
)"

IFS=$'\t' read -r pack_id pack_version <<<"${manifest_summary}"
zip_name="${pack_id}-${pack_version}.zip"

mkdir -p "${dist_dir}"
rm -rf "${stage_dir}"
mkdir -p "${stage_dir}"
cp -R "${pack_dir}/." "${stage_dir}/"

(
  cd "${stage_dir}"
  while IFS= read -r relative_path; do
    sha256sum "${relative_path}"
  done < <(find . -type f ! -name checksum.sha256 -printf '%P\n' | sort) > checksum.sha256
)

bash "${script_dir}/validate-pack.sh" "${stage_dir}"
rm -f "${dist_dir}/${zip_name}"

(
  cd "${stage_dir}"
  zip -qr "${dist_dir}/${zip_name}" .
)

printf 'Built %s\n' "${dist_dir}/${zip_name}"
