import { AppShell } from "../../components/layout/AppShell";

const metricCards = [
  { label: "Due Today", value: "18", detail: "maintenance and supervision tasks" },
  { label: "Urgent Changes", value: "4", detail: "awaiting supervisor approval" },
  { label: "Customer Alerts", value: "2", detail: "new complaints in review" },
];

export function PrsWorkbenchPage() {
  return (
    <AppShell
      eyebrow="PRS field console"
      title="Today's Field Work"
      description="Move quickly from site arrival to inspection, plant-change intake, and task feedback without falling back to desktop-style workflows."
      heroAction={
        <button
          style={{
            minHeight: "52px",
            padding: "0 22px",
            borderRadius: "999px",
            background: "linear-gradient(135deg, var(--prs-primary), var(--prs-primary-strong))",
            color: "#fff",
            fontWeight: 700,
            boxShadow: "0 12px 30px rgba(18, 87, 47, 0.24)",
          }}
          type="button"
        >
          Start Inspection
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
        {metricCards.map((card) => (
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
      <section
        style={{
          display: "grid",
          gap: "16px",
          gridTemplateColumns: "2fr 1fr",
        }}
      >
        <article
          style={{
            borderRadius: "var(--prs-radius-lg)",
            background: "var(--prs-surface-strong)",
            border: "1px solid var(--prs-line)",
            padding: "20px",
          }}
        >
          <h2 style={{ margin: 0, fontSize: "1.15rem" }}>Next on site</h2>
          <ul style={{ margin: "16px 0 0", padding: 0, listStyle: "none", display: "grid", gap: "12px" }}>
            <li>08:30 Shanghai IFC Tower · weekly maintenance</li>
            <li>10:10 Greenlane Plaza · complaint follow-up and photo capture</li>
            <li>13:40 Lakeside Center · replacement confirmation and signoff</li>
          </ul>
        </article>
        <article
          style={{
            borderRadius: "var(--prs-radius-lg)",
            background: "#17381f",
            color: "#f5fff6",
            padding: "20px",
          }}
        >
          <h2 style={{ margin: 0, fontSize: "1.15rem" }}>AI assist</h2>
          <p style={{ margin: "16px 0 0", lineHeight: 1.6, color: "rgba(245, 255, 246, 0.76)" }}>
            Capture a photo or voice note and let PRS draft the change request before supervisor review.
          </p>
        </article>
      </section>
    </AppShell>
  );
}
