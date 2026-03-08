import { BackOfficeShell } from "../../components/layout/BackOfficeShell";

const differences = [
  {
    id: "DIFF-001",
    projectId: "PRJ-001",
    period: "2026-03",
    summary: "Amount mismatch",
    source: "PLAN-001",
    owner: "procurement",
  },
  {
    id: "DIFF-002",
    projectId: "PRJ-002",
    period: "2026-03",
    summary: "Missing in ledger",
    source: "SRV-102",
    owner: "maintenance",
  },
];

export function ReconciliationWorkbenchPage() {
  return (
    <BackOfficeShell
      eyebrow="PRS finance reconciliation"
      title="Reconciliation Workbench"
      description="Compare project finance facts with manual books, isolate differences by project and source document, and route ownership to the right role."
      actions={
        <button
          style={{
            minHeight: "46px",
            padding: "0 18px",
            borderRadius: "14px",
            background: "#102530",
            color: "#fff",
            fontWeight: 700,
          }}
          type="button"
        >
          Import Ledger
        </button>
      }
    >
      <section style={{ display: "grid", gap: "16px" }}>
        {differences.map((difference) => (
          <article
            key={difference.id}
            style={{
              borderRadius: "var(--prs-radius-lg)",
              background: "var(--prs-surface-strong)",
              border: "1px solid var(--prs-line)",
              padding: "18px",
              display: "grid",
              gap: "10px",
            }}
          >
            <div style={{ display: "flex", justifyContent: "space-between", gap: "12px", alignItems: "start" }}>
              <div>
                <h2 style={{ margin: 0, fontSize: "1.05rem" }}>{difference.projectId}</h2>
                <div style={{ marginTop: "8px", color: "var(--prs-text-muted)" }}>
                  {difference.period} · {difference.source}
                </div>
              </div>
              <div
                style={{
                  minWidth: "110px",
                  padding: "8px 10px",
                  borderRadius: "999px",
                  background: "rgba(160, 45, 34, 0.12)",
                  color: "#8f291d",
                  fontWeight: 700,
                  textAlign: "center",
                }}
              >
                {difference.summary}
              </div>
            </div>
            <div style={{ color: "var(--prs-text-muted)" }}>Owner: {difference.owner}</div>
          </article>
        ))}
      </section>
    </BackOfficeShell>
  );
}
