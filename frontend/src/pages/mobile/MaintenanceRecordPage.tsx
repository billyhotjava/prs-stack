import { AppShell } from "../../components/layout/AppShell";

const completionCards = [
  { label: "Maintenance done", value: "12", detail: "visits closed with before/after evidence" },
  { label: "Watering rounds", value: "9", detail: "hydration records synced into customer feed" },
  { label: "Supervisor passes", value: "7", detail: "quality checks cleared on the first review" },
];

const feedEntries = [
  {
    id: "FEED-001",
    title: "Reception East · Ficus lyrata",
    summary: "Watering completed and plant condition verified",
    meta: "2026-03-08 · Li Wei · 3.5L applied",
  },
  {
    id: "FEED-002",
    title: "North Corridor · Kentia palm",
    summary: "Leaf trim completed and no follow-up issues found",
    meta: "2026-03-08 · Chen Yu · Supervisor passed",
  },
];

export function MaintenanceRecordPage() {
  return (
    <AppShell
      eyebrow="PRS service fulfillment"
      title="Service Feed"
      description="Capture maintenance, watering, and supervision in one pass so the same record can power internal execution and customer-facing transparency."
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
          Publish To Customer
        </button>
      }
    >
      <section
        style={{
          display: "grid",
          gap: "16px",
          gridTemplateColumns: "repeat(auto-fit, minmax(180px, 1fr))",
        }}
      >
        {completionCards.map((card) => (
          <article
            key={card.label}
            style={{
              borderRadius: "var(--prs-radius-lg)",
              background: "var(--prs-surface-strong)",
              border: "1px solid var(--prs-line)",
              padding: "18px",
            }}
          >
            <div style={{ color: "var(--prs-text-muted)", fontSize: "0.92rem" }}>{card.label}</div>
            <div style={{ marginTop: "8px", fontSize: "2rem", fontWeight: 800 }}>{card.value}</div>
            <div style={{ marginTop: "8px", color: "var(--prs-text-muted)", lineHeight: 1.5 }}>{card.detail}</div>
          </article>
        ))}
      </section>
      <section style={{ display: "grid", gap: "16px" }}>
        {feedEntries.map((entry) => (
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
                  minWidth: "86px",
                  padding: "8px 10px",
                  borderRadius: "999px",
                  background: "rgba(29, 122, 67, 0.12)",
                  textAlign: "center",
                  color: "var(--prs-primary-strong)",
                  fontWeight: 700,
                }}
              >
                Ready
              </div>
            </div>
            <div style={{ color: "var(--prs-text-muted)", fontSize: "0.92rem" }}>{entry.meta}</div>
          </article>
        ))}
      </section>
    </AppShell>
  );
}
