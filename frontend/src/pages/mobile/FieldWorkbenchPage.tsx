import { PhotoVoiceIntakeCard } from "../../components/intake/PhotoVoiceIntakeCard";
import { AppShell } from "../../components/layout/AppShell";

const taskMetrics = [
  { label: "Arrival windows", value: "3", detail: "sites to check into before noon" },
  { label: "Plant changes", value: "2", detail: "drafts waiting for supervisor review" },
  { label: "Customer signoffs", value: "1", detail: "completion to confirm on site" },
];

export function FieldWorkbenchPage() {
  return (
    <AppShell
      eyebrow="PRS field console"
      title="Today's Field Work"
      description="Move from arrival to maintenance, change intake, and signoff with short, mobile-first actions designed for one-handed use in the field."
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
          Start Arrival Flow
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
        {taskMetrics.map((card) => (
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
            <li>08:30 Shanghai IFC Tower · arrival check-in and reception inspection</li>
            <li>10:10 Greenlane Plaza · weekly maintenance and corridor watering</li>
            <li>13:40 Lakeside Center · replacement confirmation and customer signoff</li>
          </ul>
        </article>
        <PhotoVoiceIntakeCard />
      </section>
    </AppShell>
  );
}
