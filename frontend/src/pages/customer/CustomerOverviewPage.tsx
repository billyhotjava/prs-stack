import { AppShell } from "../../components/layout/AppShell";

const overviewCards = [
  { label: "Active projects", value: "2", detail: "sites currently covered under your service plan" },
  { label: "Visible service logs", value: "18", detail: "maintenance, watering, and supervision entries this month" },
  { label: "Latest sync", value: "2h", detail: "time since the last customer-visible update" },
];

const projectRows = [
  { id: "PRJ-001", name: "Shanghai IFC Tower", health: "96%", lastVisit: "Today 10:10" },
  { id: "PRJ-002", name: "Lakeside Center", health: "94%", lastVisit: "Yesterday 16:30" },
];

export function CustomerOverviewPage() {
  return (
    <AppShell
      eyebrow="PRS customer overview"
      title="Project Overview"
      description="Track project health, service coverage, and the latest maintenance activity without stepping into internal operations screens."
      heroAction={
        <button
          style={{
            minHeight: "52px",
            padding: "0 22px",
            borderRadius: "999px",
            background: "#102530",
            color: "#fff",
            fontWeight: 700,
          }}
          type="button"
        >
          Open Full Service History
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
        {overviewCards.map((card) => (
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
        {projectRows.map((project) => (
          <article
            key={project.id}
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
                <h2 style={{ margin: 0, fontSize: "1.05rem" }}>{project.name}</h2>
                <div style={{ marginTop: "8px", color: "var(--prs-text-muted)" }}>Last visit {project.lastVisit}</div>
              </div>
              <div
                style={{
                  minWidth: "88px",
                  padding: "8px 10px",
                  borderRadius: "999px",
                  background: "rgba(29, 122, 67, 0.12)",
                  color: "var(--prs-primary-strong)",
                  fontWeight: 700,
                  textAlign: "center",
                }}
              >
                {project.health}
              </div>
            </div>
          </article>
        ))}
      </section>
    </AppShell>
  );
}
