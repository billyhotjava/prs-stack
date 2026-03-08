import { AppShell } from "../../components/layout/AppShell";

const feedbackTypes = [
  { label: "Complaint", detail: "Report service quality issues or plant health risks that need urgent review." },
  { label: "Additional Request", detail: "Ask for extra plants, refresh work, or new scoped services." },
  { label: "General Feedback", detail: "Share suggestions or praise without opening formal operational work." },
];

export function SubmitFeedbackPage() {
  return (
    <AppShell
      eyebrow="PRS customer requests"
      title="Submit Feedback"
      description="Send complaints, additional requests, or general feedback into the operations review queue before any formal field work is created."
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
          Send To Operations Review
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
        {feedbackTypes.map((item) => (
          <article
            key={item.label}
            style={{
              borderRadius: "var(--prs-radius-lg)",
              background: "var(--prs-surface-strong)",
              border: "1px solid var(--prs-line)",
              padding: "18px",
              display: "grid",
              gap: "10px",
            }}
          >
            <h2 style={{ margin: 0, fontSize: "1.05rem" }}>{item.label}</h2>
            <p style={{ margin: 0, color: "var(--prs-text-muted)", lineHeight: 1.6 }}>{item.detail}</p>
          </article>
        ))}
      </section>
    </AppShell>
  );
}
