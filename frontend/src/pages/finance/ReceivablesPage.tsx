import { BackOfficeShell } from "../../components/layout/BackOfficeShell";

const cards = [
  { label: "Invoices issued", value: "1.2k", detail: "current period billed amount" },
  { label: "Collections posted", value: "800", detail: "receipts already confirmed" },
  { label: "Outstanding", value: "400", detail: "remaining amount awaiting collection" },
];

const receivableRows = [
  { invoiceNumber: "FP-2026-001", collectionRef: "HK-2026-001", projectId: "PRJ-001", amount: "1,200.00 / 800.00" },
  { invoiceNumber: "FP-2026-002", collectionRef: "Pending", projectId: "PRJ-002", amount: "950.00 / 0.00" },
];

export function ReceivablesPage() {
  return (
    <BackOfficeShell
      eyebrow="PRS business finance"
      title="Receivables & Payables"
      description="Track invoice, collection, reimbursement, and advance facts at project level without stepping into general ledger complexity."
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
          Record Finance Fact
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
        {cards.map((card) => (
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
        {receivableRows.map((row) => (
          <article
            key={row.invoiceNumber}
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
                <h2 style={{ margin: 0, fontSize: "1.05rem" }}>{row.invoiceNumber}</h2>
                <div style={{ marginTop: "8px", color: "var(--prs-text-muted)" }}>
                  {row.projectId} · {row.collectionRef}
                </div>
              </div>
              <div
                style={{
                  minWidth: "132px",
                  padding: "8px 10px",
                  borderRadius: "999px",
                  background: "rgba(29, 122, 67, 0.12)",
                  color: "var(--prs-primary-strong)",
                  fontWeight: 700,
                  textAlign: "center",
                }}
              >
                {row.amount}
              </div>
            </div>
          </article>
        ))}
      </section>
    </BackOfficeShell>
  );
}
