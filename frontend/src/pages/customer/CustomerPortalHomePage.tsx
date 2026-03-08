import { AppShell } from "../../components/layout/AppShell";

const serviceMoments = [
  "Weekly maintenance completed at Shanghai IFC Tower",
  "Automatic watering log synced from Lakeside Center",
  "Supervisor review cleared the latest plant replacement request",
];

const openRequests = [
  "Lobby ficus leaf browning follow-up",
  "Additional orchids requested for reception desk",
];

export function CustomerPortalHomePage() {
  return (
    <AppShell
      eyebrow="PRS customer portal"
      title="Your Plant Service Window"
      description="Follow maintenance visits, watering records, and active service requests without waiting for an internal status update."
      heroAction={
        <button
          style={{
            minHeight: "52px",
            padding: "0 22px",
            borderRadius: "999px",
            background: "var(--prs-text)",
            color: "#fff",
            fontWeight: 700,
            border: "none",
          }}
          type="button"
        >
          Request Service
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
        <article
          style={{
            borderRadius: "var(--prs-radius-lg)",
            padding: "18px",
            border: "1px solid var(--prs-line)",
            background: "var(--prs-surface-strong)",
          }}
        >
          <div style={{ color: "var(--prs-text-muted)" }}>Plant health coverage</div>
          <div style={{ marginTop: "8px", fontSize: "2rem", fontWeight: 800 }}>96%</div>
          <div style={{ marginTop: "8px", color: "var(--prs-text-muted)" }}>assets inspected on schedule this month</div>
        </article>
        <article
          style={{
            borderRadius: "var(--prs-radius-lg)",
            padding: "18px",
            border: "1px solid var(--prs-line)",
            background: "linear-gradient(135deg, rgba(18, 87, 47, 0.08), rgba(216, 239, 149, 0.4))",
          }}
        >
          <div style={{ color: "var(--prs-text-muted)" }}>Last watering sync</div>
          <div style={{ marginTop: "8px", fontSize: "2rem", fontWeight: 800 }}>2h ago</div>
          <div style={{ marginTop: "8px", color: "var(--prs-text-muted)" }}>latest record from the reception cluster</div>
        </article>
      </section>
      <section
        style={{
          display: "grid",
          gap: "16px",
          gridTemplateColumns: "repeat(auto-fit, minmax(260px, 1fr))",
        }}
      >
        <article
          style={{
            borderRadius: "var(--prs-radius-lg)",
            padding: "20px",
            border: "1px solid var(--prs-line)",
            background: "var(--prs-surface-strong)",
          }}
        >
          <h2 style={{ margin: 0, fontSize: "1.1rem" }}>Recent service moments</h2>
          <ul style={{ margin: "16px 0 0", padding: 0, listStyle: "none", display: "grid", gap: "12px" }}>
            {serviceMoments.map((item) => (
              <li key={item} style={{ color: "var(--prs-text-muted)", lineHeight: 1.5 }}>
                {item}
              </li>
            ))}
          </ul>
        </article>
        <article
          style={{
            borderRadius: "var(--prs-radius-lg)",
            padding: "20px",
            background: "#17381f",
            color: "#f3fff2",
          }}
        >
          <h2 style={{ margin: 0, fontSize: "1.1rem" }}>Open requests</h2>
          <ul style={{ margin: "16px 0 0", padding: 0, listStyle: "none", display: "grid", gap: "12px" }}>
            {openRequests.map((item) => (
              <li key={item} style={{ color: "rgba(243, 255, 242, 0.8)", lineHeight: 1.5 }}>
                {item}
              </li>
            ))}
          </ul>
        </article>
      </section>
    </AppShell>
  );
}
