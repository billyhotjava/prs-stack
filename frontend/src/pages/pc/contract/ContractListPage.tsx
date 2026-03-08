import { BackOfficeShell } from "../../../components/layout/BackOfficeShell";

const contracts = [
  {
    code: "HT-2026-001",
    customer: "IFC Property",
    projectCount: 5,
    billingMode: "Monthly rental",
    annualValue: "¥1.28M",
  },
  {
    code: "HT-2026-014",
    customer: "Greenlane Plaza",
    projectCount: 3,
    billingMode: "Quarterly rental",
    annualValue: "¥860K",
  },
];

export function ContractListPage() {
  return (
    <BackOfficeShell
      eyebrow="PRS back office"
      title="Contract Ledger"
      description="Review commercial scope, billing patterns, and contract-to-project coverage before operational work starts in the field."
    >
      <div
        style={{
          display: "grid",
          gap: "18px",
          gridTemplateColumns: "repeat(auto-fit, minmax(280px, 1fr))",
        }}
      >
        {contracts.map((contract) => (
          <article
            key={contract.code}
            style={{
              borderRadius: "var(--prs-radius-lg)",
              background: "rgba(255, 255, 255, 0.92)",
              border: "1px solid var(--prs-line)",
              padding: "22px",
            }}
          >
            <div style={{ color: "var(--prs-text-muted)", fontSize: "0.92rem" }}>{contract.code}</div>
            <h2 style={{ margin: "8px 0 0", fontSize: "1.25rem" }}>{contract.customer}</h2>
            <dl style={{ margin: "18px 0 0", display: "grid", gap: "10px" }}>
              <div style={{ display: "flex", justifyContent: "space-between", gap: "12px" }}>
                <dt style={{ color: "var(--prs-text-muted)" }}>Projects</dt>
                <dd style={{ margin: 0, fontWeight: 700 }}>{contract.projectCount}</dd>
              </div>
              <div style={{ display: "flex", justifyContent: "space-between", gap: "12px" }}>
                <dt style={{ color: "var(--prs-text-muted)" }}>Billing</dt>
                <dd style={{ margin: 0 }}>{contract.billingMode}</dd>
              </div>
              <div style={{ display: "flex", justifyContent: "space-between", gap: "12px" }}>
                <dt style={{ color: "var(--prs-text-muted)" }}>Annual value</dt>
                <dd style={{ margin: 0, fontWeight: 700 }}>{contract.annualValue}</dd>
              </div>
            </dl>
          </article>
        ))}
      </div>
    </BackOfficeShell>
  );
}
