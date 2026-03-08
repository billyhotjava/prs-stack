import { AppShell } from "../../components/layout/AppShell";

const executiveCards = [
  { label: "Revenue in view", value: "¥1.20M", detail: "invoice and collection facts consolidated" },
  { label: "Reconciliation risk", value: "1", detail: "differences waiting on finance confirmation" },
  { label: "Complaint trend", value: "2", detail: "feedback items still inside attention scope" },
];

const anomalyTimeline = [
  "09:00 Procurement variance detected on replacement plan PLAN-001",
  "11:20 Reconciliation mismatch flagged for March project costs",
  "15:40 Customer complaint reopened for lobby ficus follow-up",
];

const aiOperatingBrief = [
  "Margin remains positive, but procurement deltas now drive most variance.",
  "Finance and operations should resolve the current mismatch before month-end close.",
  "Complaint count is low, but the open items overlap with high-visibility projects.",
];

export function OperatingCockpitPage() {
  return (
    <AppShell
      eyebrow="PRS executive operating cockpit"
      title="Operating Cockpit"
      description="Read profitability, reconciliation risk, service quality, and AI interpretation in one concise operating surface designed for daily leadership review."
      heroAction={
        <button
          style={{
            minHeight: "52px",
            padding: "0 22px",
            borderRadius: "999px",
            background: "linear-gradient(135deg, #0f4f74, #1d7a43)",
            color: "#fff",
            fontWeight: 700,
          }}
          type="button"
        >
          Ask PRS AI
        </button>
      }
    >
      <section
        style={{
          display: "grid",
          gap: "16px",
          gridTemplateColumns: "repeat(auto-fit, minmax(200px, 1fr))",
        }}
      >
        {executiveCards.map((card) => (
          <article
            key={card.label}
            style={{
              borderRadius: "var(--prs-radius-lg)",
              padding: "20px",
              background: "var(--prs-surface-strong)",
              border: "1px solid var(--prs-line)",
            }}
          >
            <div style={{ color: "var(--prs-text-muted)" }}>{card.label}</div>
            <div style={{ marginTop: "10px", fontSize: "2rem", fontWeight: 800 }}>{card.value}</div>
            <div style={{ marginTop: "10px", color: "var(--prs-text-muted)", lineHeight: 1.5 }}>{card.detail}</div>
          </article>
        ))}
      </section>
      <section
        style={{
          display: "grid",
          gap: "16px",
          gridTemplateColumns: "1.1fr 1fr",
        }}
      >
        <article
          style={{
            borderRadius: "var(--prs-radius-lg)",
            padding: "20px",
            background: "linear-gradient(135deg, rgba(15, 79, 116, 0.08), rgba(255, 255, 255, 0.92))",
            border: "1px solid var(--prs-line)",
          }}
        >
          <h2 style={{ margin: 0, fontSize: "1.1rem" }}>Anomaly timeline</h2>
          <ul style={{ margin: "16px 0 0", padding: 0, listStyle: "none", display: "grid", gap: "12px" }}>
            {anomalyTimeline.map((item) => (
              <li key={item} style={{ color: "var(--prs-text-muted)", lineHeight: 1.6 }}>
                {item}
              </li>
            ))}
          </ul>
        </article>
        <article
          style={{
            borderRadius: "var(--prs-radius-lg)",
            padding: "20px",
            background: "#102530",
            color: "#eff9ff",
          }}
        >
          <h2 style={{ margin: 0, fontSize: "1.1rem" }}>AI operating brief</h2>
          <ul style={{ margin: "16px 0 0", padding: 0, listStyle: "none", display: "grid", gap: "12px" }}>
            {aiOperatingBrief.map((item) => (
              <li key={item} style={{ color: "rgba(239, 249, 255, 0.82)", lineHeight: 1.6 }}>
                {item}
              </li>
            ))}
          </ul>
        </article>
      </section>
    </AppShell>
  );
}
