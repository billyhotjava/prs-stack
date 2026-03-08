import { PositionTreePage } from "./PositionTreePage";

type ProjectDetailPageProps = {
  project: {
    name: string;
    customer: string;
    contractCode: string;
    projectManager: string;
    activePositions: number;
    monthlyRevenue: string;
  };
};

export function ProjectDetailPage({ project }: ProjectDetailPageProps) {
  return (
    <div style={{ display: "grid", gap: "18px" }}>
      <article
        style={{
          borderRadius: "var(--prs-radius-lg)",
          background: "rgba(255, 255, 255, 0.92)",
          border: "1px solid var(--prs-line)",
          padding: "22px",
        }}
      >
        <div style={{ color: "var(--prs-text-muted)", fontSize: "0.92rem" }}>Selected project</div>
        <h2 style={{ margin: "8px 0 0", fontSize: "1.35rem" }}>{project.name}</h2>
        <dl style={{ margin: "18px 0 0", display: "grid", gap: "12px" }}>
          <div style={{ display: "flex", justifyContent: "space-between", gap: "12px" }}>
            <dt style={{ color: "var(--prs-text-muted)" }}>Customer</dt>
            <dd style={{ margin: 0 }}>{project.customer}</dd>
          </div>
          <div style={{ display: "flex", justifyContent: "space-between", gap: "12px" }}>
            <dt style={{ color: "var(--prs-text-muted)" }}>Contract</dt>
            <dd style={{ margin: 0 }}>{project.contractCode}</dd>
          </div>
          <div style={{ display: "flex", justifyContent: "space-between", gap: "12px" }}>
            <dt style={{ color: "var(--prs-text-muted)" }}>Project manager</dt>
            <dd style={{ margin: 0 }}>{project.projectManager}</dd>
          </div>
          <div style={{ display: "flex", justifyContent: "space-between", gap: "12px" }}>
            <dt style={{ color: "var(--prs-text-muted)" }}>Active positions</dt>
            <dd style={{ margin: 0, fontWeight: 700 }}>{project.activePositions}</dd>
          </div>
          <div style={{ display: "flex", justifyContent: "space-between", gap: "12px" }}>
            <dt style={{ color: "var(--prs-text-muted)" }}>Monthly revenue</dt>
            <dd style={{ margin: 0, fontWeight: 700 }}>{project.monthlyRevenue}</dd>
          </div>
        </dl>
      </article>
      <PositionTreePage />
    </div>
  );
}
