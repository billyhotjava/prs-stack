import { AppShell } from "../../components/layout/AppShell";
import { TaskDetailPage } from "./TaskDetailPage";

const tasks = [
  {
    id: "TASK-001",
    title: "Arrival Check-in",
    location: "Shanghai IFC Tower · Reception East",
    dueWindow: "08:30 - 09:00",
    state: "Start now",
    checklist: ["Confirm arrival photo", "Open inspection checklist", "Check client contact"],
  },
  {
    id: "TASK-002",
    title: "Weekly Maintenance",
    location: "Greenlane Plaza · North Corridor",
    dueWindow: "10:00 - 11:30",
    state: "Queued",
    checklist: ["Water plant cluster", "Trim damaged leaves", "Upload after photos"],
  },
  {
    id: "TASK-003",
    title: "Change Signoff",
    location: "Lakeside Center · Lobby ficus",
    dueWindow: "13:40 - 14:20",
    state: "Pending signoff",
    checklist: ["Confirm replacement quality", "Collect client signature", "Close movement record"],
  },
];

const selectedTask = tasks[0];

export function MyTasksPage() {
  return (
    <AppShell
      eyebrow="PRS task inbox"
      title="My Tasks"
      description="Keep the next field actions within thumb reach so arrival, maintenance, and signoff can be completed without switching into desktop-style screens."
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
          Open Task Detail
        </button>
      }
    >
      <section style={{ display: "grid", gap: "16px" }}>
        {tasks.map((task) => (
          <article
            key={task.id}
            style={{
              borderRadius: "var(--prs-radius-lg)",
              background: task.id === selectedTask.id ? "rgba(216, 239, 149, 0.24)" : "var(--prs-surface-strong)",
              border: "1px solid var(--prs-line)",
              padding: "18px",
              display: "grid",
              gap: "10px",
            }}
          >
            <div style={{ display: "flex", justifyContent: "space-between", gap: "12px", alignItems: "start" }}>
              <div>
                <div style={{ fontWeight: 700 }}>{task.title}</div>
                <div style={{ marginTop: "6px", color: "var(--prs-text-muted)", lineHeight: 1.5 }}>
                  {task.location}
                </div>
              </div>
              <div
                style={{
                  minWidth: "92px",
                  padding: "8px 10px",
                  borderRadius: "999px",
                  background: "rgba(17, 32, 21, 0.06)",
                  textAlign: "center",
                  fontSize: "0.9rem",
                  fontWeight: 700,
                }}
              >
                {task.state}
              </div>
            </div>
            <div style={{ color: "var(--prs-text-muted)", fontSize: "0.92rem" }}>{task.dueWindow}</div>
          </article>
        ))}
      </section>
      <TaskDetailPage task={selectedTask} />
    </AppShell>
  );
}
