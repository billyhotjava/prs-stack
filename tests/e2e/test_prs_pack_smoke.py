import json
import subprocess
import unittest
from pathlib import Path


ROOT = Path(__file__).resolve().parents[2]


class PrsPackSmokeTest(unittest.TestCase):
    def test_release_artifacts_and_golive_inputs_exist(self) -> None:
        required_paths = [
            ROOT / "deploy" / "golive-checklist.md",
            ROOT / "deploy" / "init-data" / "seed-projects.json",
            ROOT / "backend" / "src" / "main" / "resources" / "db" / "changelog" / "V001__baseline.xml",
            ROOT / "backend" / "src" / "main" / "resources" / "db" / "changelog" / "V002__seed_roles.xml",
        ]

        missing = [str(path) for path in required_paths if not path.exists()]
        self.assertEqual([], missing)

        result = subprocess.run(
            ["bash", str(ROOT / "scripts" / "release-pack.sh"), "--snapshot"],
            capture_output=True,
            text=True,
            check=False,
        )

        self.assertEqual(0, result.returncode, result.stdout + result.stderr)

        release_metadata = json.loads((ROOT / "dist" / "artifacts" / "release-metadata.json").read_text(encoding="utf-8"))
        self.assertEqual("prs-pack", release_metadata["packId"])
        self.assertEqual("snapshot", release_metadata["channel"])


if __name__ == "__main__":
    unittest.main()
