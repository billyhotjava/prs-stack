import { BackOfficeShell } from "../../../components/layout/BackOfficeShell";

const triageQueue = [
  {
    id: "FDB-001",
    title: "Lobby ficus leaf browning",
    type: "Complaint",
    project: "Shanghai IFC Tower",
    requester: "Ms. Lin",
    priority: "High",
  },
  {
    id: "FDB-002",
    title: "Additional orchids for reception desk",
    type: "Additional Request",
    project: "Lakeside Center",
    requester: "Mr. Zhou",
    priority: "Medium",
  },
];

export function FeedbackTriagePage() {
  return (
    <BackOfficeShell
      eyebrow="PRS operations triage"
      title="Feedback Triage"
      description="Review customer complaints and additional requests before converting them into formal plant-change or service-recovery work."
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
          Review Complaint
        </button>
      }
    >
      <section style={{ display: "grid", gap: "16px" }}>
        {triageQueue.map((item) => (
          <article
            key={item.id}
            style={{
              borderRadius: "var(--prs-radius-lg)",
              background: "var(--prs-surface-strong)",
              border: "1px solid var(--prs-line)",
              padding: "18px",
              display: "grid",
              gap: "12px",
            }}
          >
            <div style={{ display: "flex", justifyContent: "space-between", gap: "12px", alignItems: "start" }}>
              <div>
                <h2 style={{ margin: 0, fontSize: "1.05rem" }}>{item.title}</h2>
                <div style={{ marginTop: "8px", color: "var(--prs-text-muted)" }}>
                  {item.project} · {item.requester}
                </div>
              </div>
              <div
                style={{
                  minWidth: "88px",
                  padding: "8px 10px",
                  borderRadius: "999px",
                  background: item.priority === "High" ? "rgba(160, 45, 34, 0.12)" : "rgba(216, 239, 149, 0.32)",
                  color: item.priority === "High" ? "#8f291d" : "#475300",
                  fontWeight: 700,
                  textAlign: "center",
                }}
              >
                {item.priority}
              </div>
            </div>
            <div style={{ color: "var(--prs-text-muted)" }}>{item.type}</div>
          </article>
        ))}
      </section>
    </BackOfficeShell>
  );
}
