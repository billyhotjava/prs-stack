import { AppShell } from "../../components/layout/AppShell";

const historyEntries = [
  {
    id: "FEED-001",
    title: "Shanghai IFC Tower · Reception East",
    summary: "Watering completed and plant condition verified",
    meta: "2026-03-08 · Li Wei · 3.5L applied",
  },
  {
    id: "FEED-002",
    title: "Lakeside Center · Lobby ficus",
    summary: "Leaf trim completed and no follow-up issues found",
    meta: "2026-03-07 · Chen Yu · Supervisor passed",
  },
];

export function ServiceHistoryPage() {
  return (
    <AppShell
      eyebrow="PRS customer service history"
      title="Service History"
      description="Review the latest maintenance, watering, and supervision work in the same language your on-site teams recorded it."
      heroAction={
        <button
          style={{
            minHeight: "52px",
            padding: "0 22px",
            borderRadius: "999px",
            background: "linear-gradient(135deg, var(--prs-primary), var(--prs-primary-strong))",
            color: "#fff",
            fontWeight: 700,
          }}
          type="button"
        >
          Request Follow-up
        </button>
      }
    >
      <section style={{ display: "grid", gap: "16px" }}>
        {historyEntries.map((entry) => (
          <article
            key={entry.id}
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
                <h2 style={{ margin: 0, fontSize: "1.05rem" }}>{entry.title}</h2>
                <p style={{ margin: "10px 0 0", color: "var(--prs-text-muted)", lineHeight: 1.6 }}>{entry.summary}</p>
              </div>
              <div
                style={{
                  minWidth: "92px",
                  padding: "8px 10px",
                  borderRadius: "999px",
                  background: "rgba(216, 239, 149, 0.32)",
                  color: "#1d4630",
                  fontWeight: 700,
                  textAlign: "center",
                }}
              >
                Shared
              </div>
            </div>
            <div style={{ color: "var(--prs-text-muted)", fontSize: "0.92rem" }}>{entry.meta}</div>
          </article>
        ))}
      </section>
    </AppShell>
  );
}
