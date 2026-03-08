import { BackOfficeShell } from "../../../components/layout/BackOfficeShell";

const customers = [
  {
    id: "CUS-001",
    name: "IFC Property",
    accountLead: "Grace Wu",
    activeProjects: 5,
    serviceHealth: "A",
  },
  {
    id: "CUS-002",
    name: "Greenlane Plaza",
    accountLead: "Leo Chen",
    activeProjects: 3,
    serviceHealth: "B+",
  },
  {
    id: "CUS-003",
    name: "Lakeside Center",
    accountLead: "Annie Lin",
    activeProjects: 2,
    serviceHealth: "A-",
  },
];

export function CustomerListPage() {
  return (
    <BackOfficeShell
      eyebrow="PRS back office"
      title="Customer Directory"
      description="Keep customer ownership, active projects, and service health in one desktop workspace for operations and management roles."
      actions={
        <>
          <button
            style={{
              minHeight: "44px",
              padding: "0 18px",
              borderRadius: "999px",
              background: "linear-gradient(135deg, var(--prs-primary), var(--prs-primary-strong))",
              color: "#fff",
              fontWeight: 700,
            }}
            type="button"
          >
            New Customer
          </button>
          <button
            style={{
              minHeight: "44px",
              padding: "0 18px",
              borderRadius: "999px",
              background: "rgba(17, 32, 21, 0.06)",
              color: "var(--prs-text)",
              fontWeight: 600,
            }}
            type="button"
          >
            Export
          </button>
        </>
      }
    >
      <article
        style={{
          borderRadius: "var(--prs-radius-lg)",
          background: "rgba(255, 255, 255, 0.92)",
          border: "1px solid var(--prs-line)",
          overflow: "hidden",
        }}
      >
        <table style={{ width: "100%", borderCollapse: "collapse" }}>
          <thead>
            <tr style={{ textAlign: "left", color: "var(--prs-text-muted)" }}>
              <th style={{ padding: "18px 20px" }}>Customer</th>
              <th style={{ padding: "18px 20px" }}>Account Lead</th>
              <th style={{ padding: "18px 20px" }}>Active Projects</th>
              <th style={{ padding: "18px 20px" }}>Service Health</th>
            </tr>
          </thead>
          <tbody>
            {customers.map((customer) => (
              <tr key={customer.id} style={{ borderTop: "1px solid var(--prs-line)" }}>
                <td style={{ padding: "18px 20px", fontWeight: 700 }}>{customer.name}</td>
                <td style={{ padding: "18px 20px" }}>{customer.accountLead}</td>
                <td style={{ padding: "18px 20px" }}>{customer.activeProjects}</td>
                <td style={{ padding: "18px 20px" }}>{customer.serviceHealth}</td>
              </tr>
            ))}
          </tbody>
        </table>
      </article>
    </BackOfficeShell>
  );
}
