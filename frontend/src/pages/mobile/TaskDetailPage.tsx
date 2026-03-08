type TaskDetailPageProps = {
  task: {
    title: string;
    location: string;
    dueWindow: string;
    checklist: string[];
  };
};

export function TaskDetailPage({ task }: TaskDetailPageProps) {
  return (
    <article
      style={{
        borderRadius: "var(--prs-radius-lg)",
        background: "var(--prs-surface-strong)",
        border: "1px solid var(--prs-line)",
        padding: "18px",
        display: "grid",
        gap: "16px",
      }}
    >
      <div>
        <div style={{ color: "var(--prs-text-muted)", fontSize: "0.9rem" }}>Task detail</div>
        <h2 style={{ margin: "8px 0 0", fontSize: "1.2rem" }}>{task.title}</h2>
      </div>
      <dl style={{ margin: 0, display: "grid", gap: "10px" }}>
        <div style={{ display: "flex", justifyContent: "space-between", gap: "12px" }}>
          <dt style={{ color: "var(--prs-text-muted)" }}>Location</dt>
          <dd style={{ margin: 0, textAlign: "right" }}>{task.location}</dd>
        </div>
        <div style={{ display: "flex", justifyContent: "space-between", gap: "12px" }}>
          <dt style={{ color: "var(--prs-text-muted)" }}>Due window</dt>
          <dd style={{ margin: 0, textAlign: "right" }}>{task.dueWindow}</dd>
        </div>
      </dl>
      <div>
        <div style={{ fontWeight: 700 }}>Checklist</div>
        <ul style={{ margin: "12px 0 0", paddingLeft: "18px", display: "grid", gap: "8px" }}>
          {task.checklist.map((item) => (
            <li key={item}>{item}</li>
          ))}
        </ul>
      </div>
      <div style={{ display: "grid", gap: "10px", gridTemplateColumns: "repeat(2, minmax(0, 1fr))" }}>
        <button
          style={{
            minHeight: "46px",
            borderRadius: "14px",
            background: "rgba(17, 32, 21, 0.06)",
            color: "var(--prs-text)",
            fontWeight: 700,
          }}
          type="button"
        >
          Arrived On Site
        </button>
        <button
          style={{
            minHeight: "46px",
            borderRadius: "14px",
            background: "linear-gradient(135deg, var(--prs-primary), var(--prs-primary-strong))",
            color: "#fff",
            fontWeight: 700,
          }}
          type="button"
        >
          Complete Task
        </button>
      </div>
    </article>
  );
}
