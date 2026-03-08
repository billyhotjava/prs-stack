import { AppShell } from "../../components/layout/AppShell";

const intakeCards = [
  { label: "Photo Intake", detail: "Attach before/after images so AI can detect plant condition and placement context." },
  { label: "Voice Notes", detail: "Capture现场描述 and let PRS draft the replacement summary from spoken notes." },
  { label: "Structured Details", detail: "Confirm project, position, change type, and customer impact before submission." },
];

export function PlantChangeDraftPage() {
  return (
    <AppShell
      eyebrow="PRS plant change intake"
      title="Plant Change Draft"
      description="Combine photo, voice, and structured form input into a single draft that must be reviewed by a supervisor before execution starts."
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
          Submit For Supervisor Approval
        </button>
      }
    >
      <section
        style={{
          display: "grid",
          gap: "16px",
          gridTemplateColumns: "repeat(auto-fit, minmax(220px, 1fr))",
        }}
      >
        {intakeCards.map((card) => (
          <article
            key={card.label}
            style={{
              borderRadius: "var(--prs-radius-lg)",
              background: "var(--prs-surface-strong)",
              border: "1px solid var(--prs-line)",
              padding: "18px",
              display: "grid",
              gap: "10px",
            }}
          >
            <h2 style={{ margin: 0, fontSize: "1.05rem" }}>{card.label}</h2>
            <p style={{ margin: 0, color: "var(--prs-text-muted)", lineHeight: 1.6 }}>{card.detail}</p>
          </article>
        ))}
      </section>
    </AppShell>
  );
}
