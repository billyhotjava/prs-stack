import { AppShell } from "../../components/layout/AppShell";

const portfolioSignals = [
  { label: "Monthly revenue", value: "¥2.48M", detail: "up 6.2% against last month" },
  { label: "Gross margin", value: "31.4%", detail: "driven by stable maintenance routes" },
  { label: "At-risk projects", value: "3", detail: "margin or complaint signal needs review" },
];

const aiBrief = [
  "One flagship lobby account shows high service quality but falling add-on revenue.",
  "Procurement cost for tropical replacements is rising faster than project pricing.",
  "Customer complaints dropped after supervisor auto-routing was enabled on urgent swaps.",
];

export function ExecutiveCockpitPage() {
  return (
    <AppShell
      eyebrow="PRS executive cockpit"
      title="Operational Pulse"
      description="Track project profitability, service quality, and exception signals from the same mobile-ready cockpit the field team is feeding in real time."
      heroAction={
        <button
          style={{
            minHeight: "52px",
            padding: "0 22px",
            borderRadius: "999px",
            background: "linear-gradient(135deg, #0f4f74, #1d7a43)",
            color: "#fff",
            fontWeight: 700,
            border: "none",
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
        {portfolioSignals.map((signal) => (
          <article
            key={signal.label}
            style={{
              borderRadius: "var(--prs-radius-lg)",
              padding: "20px",
              background: "var(--prs-surface-strong)",
              border: "1px solid var(--prs-line)",
            }}
          >
            <div style={{ color: "var(--prs-text-muted)" }}>{signal.label}</div>
            <div style={{ marginTop: "10px", fontSize: "2rem", fontWeight: 800 }}>{signal.value}</div>
            <div style={{ marginTop: "10px", color: "var(--prs-text-muted)", lineHeight: 1.5 }}>{signal.detail}</div>
          </article>
        ))}
      </section>
      <section
        style={{
          display: "grid",
          gap: "16px",
          gridTemplateColumns: "1.2fr 1fr",
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
          <h2 style={{ margin: 0, fontSize: "1.1rem" }}>AI daily brief</h2>
          <ul style={{ margin: "16px 0 0", padding: 0, listStyle: "none", display: "grid", gap: "12px" }}>
            {aiBrief.map((item) => (
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
          <h2 style={{ margin: 0, fontSize: "1.1rem" }}>Focus queue</h2>
          <p style={{ margin: "16px 0 0", lineHeight: 1.6, color: "rgba(239, 249, 255, 0.8)" }}>
            Review margin variance on East Bund Tower, inspect the supplier price jump on tropical swaps, and confirm that customer-response SLAs remain inside the target window.
          </p>
        </article>
      </section>
    </AppShell>
  );
}
