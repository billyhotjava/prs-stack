import { BackOfficeShell } from "../../../components/layout/BackOfficeShell";
import { ProjectDetailPage } from "./ProjectDetailPage";

const projects = [
  {
    code: "PRJ-001",
    name: "Shanghai IFC Tower",
    customer: "IFC Property",
    contractCode: "HT-2026-001",
    projectManager: "Grace Wu",
    activePositions: 34,
    monthlyRevenue: "¥128K",
    serviceHealth: "A",
  },
  {
    code: "PRJ-014",
    name: "Greenlane Plaza",
    customer: "Greenlane Plaza",
    contractCode: "HT-2026-014",
    projectManager: "Leo Chen",
    activePositions: 19,
    monthlyRevenue: "¥82K",
    serviceHealth: "B+",
  },
  {
    code: "PRJ-020",
    name: "Lakeside Center",
    customer: "Lakeside Center",
    contractCode: "HT-2026-020",
    projectManager: "Annie Lin",
    activePositions: 12,
    monthlyRevenue: "¥61K",
    serviceHealth: "A-",
  },
];

const selectedProject = projects[0];

const fieldStyle = {
  display: "grid",
  gap: "8px",
} as const;

const inputStyle = {
  minHeight: "44px",
  borderRadius: "14px",
  border: "1px solid var(--prs-line)",
  background: "#fff",
  padding: "0 14px",
  color: "var(--prs-text)",
} as const;

export function ProjectListPage() {
  return (
    <BackOfficeShell
      eyebrow="PRS back office"
      title="Project Master Data"
      description="Drive the desktop operating model for contracts, projects, and position structures before the field team starts placement, service, and change work."
      actions={
        <>
          <button
            style={{
              minHeight: "46px",
              padding: "0 20px",
              borderRadius: "999px",
              background: "linear-gradient(135deg, var(--prs-primary), var(--prs-primary-strong))",
              color: "#fff",
              fontWeight: 700,
            }}
            type="button"
          >
            New Project
          </button>
          <button
            style={{
              minHeight: "46px",
              padding: "0 20px",
              borderRadius: "999px",
              background: "rgba(17, 32, 21, 0.06)",
              color: "var(--prs-text)",
              fontWeight: 600,
            }}
            type="button"
          >
            Export Portfolio
          </button>
        </>
      }
      filters={
        <div
          style={{
            display: "grid",
            gap: "14px",
            gridTemplateColumns: "1.4fr 1fr 1fr auto",
            alignItems: "end",
          }}
        >
          <label style={fieldStyle}>
            <span style={{ color: "var(--prs-text-muted)", fontSize: "0.92rem" }}>Search project</span>
            <input defaultValue="Shanghai" style={inputStyle} />
          </label>
          <label style={fieldStyle}>
            <span style={{ color: "var(--prs-text-muted)", fontSize: "0.92rem" }}>Customer</span>
            <select defaultValue="all" style={inputStyle}>
              <option value="all">All customers</option>
              <option value="ifc">IFC Property</option>
            </select>
          </label>
          <label style={fieldStyle}>
            <span style={{ color: "var(--prs-text-muted)", fontSize: "0.92rem" }}>Service health</span>
            <select defaultValue="all" style={inputStyle}>
              <option value="all">All ratings</option>
              <option value="a">A and above</option>
            </select>
          </label>
          <button
            style={{
              minHeight: "44px",
              borderRadius: "14px",
              background: "#102530",
              color: "#fff",
              padding: "0 18px",
              fontWeight: 700,
            }}
            type="button"
          >
            Apply Filters
          </button>
        </div>
      }
    >
      <section
        style={{
          display: "grid",
          gap: "20px",
          gridTemplateColumns: "minmax(0, 1.65fr) minmax(340px, 0.95fr)",
          alignItems: "start",
        }}
      >
        <article
          style={{
            borderRadius: "var(--prs-radius-lg)",
            background: "rgba(255, 255, 255, 0.94)",
            border: "1px solid var(--prs-line)",
            overflow: "hidden",
          }}
        >
          <div
            style={{
              display: "flex",
              justifyContent: "space-between",
              alignItems: "center",
              padding: "20px",
              borderBottom: "1px solid var(--prs-line)",
            }}
          >
            <div>
              <h2 style={{ margin: 0, fontSize: "1.15rem" }}>Portfolio Summary</h2>
              <p style={{ margin: "6px 0 0", color: "var(--prs-text-muted)" }}>
                Sticky filters stay in place while you scan dense project data.
              </p>
            </div>
            <div style={{ color: "var(--prs-text-muted)", fontWeight: 600 }}>{projects.length} active projects</div>
          </div>
          <table style={{ width: "100%", borderCollapse: "collapse" }}>
            <thead>
              <tr style={{ textAlign: "left", color: "var(--prs-text-muted)" }}>
                <th style={{ padding: "18px 20px" }}>Project</th>
                <th style={{ padding: "18px 20px" }}>Customer</th>
                <th style={{ padding: "18px 20px" }}>Positions</th>
                <th style={{ padding: "18px 20px" }}>Revenue</th>
                <th style={{ padding: "18px 20px" }}>Health</th>
              </tr>
            </thead>
            <tbody>
              {projects.map((project) => (
                <tr
                  key={project.code}
                  style={{
                    borderTop: "1px solid var(--prs-line)",
                    background: project.code === selectedProject.code ? "rgba(216, 239, 149, 0.16)" : "transparent",
                  }}
                >
                  <td style={{ padding: "18px 20px" }}>
                    <div style={{ fontWeight: 700 }}>{project.name}</div>
                    <div style={{ color: "var(--prs-text-muted)", marginTop: "4px", fontSize: "0.92rem" }}>
                      {project.contractCode}
                    </div>
                  </td>
                  <td style={{ padding: "18px 20px" }}>{project.customer}</td>
                  <td style={{ padding: "18px 20px" }}>{project.activePositions}</td>
                  <td style={{ padding: "18px 20px", fontWeight: 700 }}>{project.monthlyRevenue}</td>
                  <td style={{ padding: "18px 20px" }}>{project.serviceHealth}</td>
                </tr>
              ))}
            </tbody>
          </table>
        </article>

        <ProjectDetailPage project={selectedProject} />
      </section>
    </BackOfficeShell>
  );
}
