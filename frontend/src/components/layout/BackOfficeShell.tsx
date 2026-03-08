import type { PropsWithChildren, ReactNode } from "react";

type BackOfficeShellProps = PropsWithChildren<{
  eyebrow: string;
  title: string;
  description: string;
  actions?: ReactNode;
  filters?: ReactNode;
}>;

export function BackOfficeShell({
  eyebrow,
  title,
  description,
  actions,
  filters,
  children,
}: BackOfficeShellProps) {
  return (
    <main style={{ padding: "32px 24px 48px" }}>
      <section
        style={{
          margin: "0 auto",
          maxWidth: "1440px",
          display: "grid",
          gap: "20px",
        }}
      >
        <header
          style={{
            display: "grid",
            gap: "18px",
            borderRadius: "var(--prs-radius-xl)",
            background: "rgba(255, 255, 255, 0.9)",
            border: "1px solid var(--prs-line)",
            boxShadow: "var(--prs-shadow)",
            padding: "28px",
          }}
        >
          <div
            style={{
              color: "var(--prs-primary-strong)",
              fontSize: "0.82rem",
              fontWeight: 700,
              letterSpacing: "0.08em",
              textTransform: "uppercase",
            }}
          >
            {eyebrow}
          </div>
          <div
            style={{
              display: "grid",
              gridTemplateColumns: "minmax(0, 1fr) auto",
              gap: "20px",
              alignItems: "end",
            }}
          >
            <div>
              <h1 style={{ margin: 0, fontSize: "clamp(2rem, 3vw, 3rem)", lineHeight: 1.05 }}>
                {title}
              </h1>
              <p
                style={{
                  margin: "12px 0 0",
                  maxWidth: "760px",
                  color: "var(--prs-text-muted)",
                  lineHeight: 1.6,
                }}
              >
                {description}
              </p>
            </div>
            {actions ? <div style={{ display: "flex", gap: "12px", flexWrap: "wrap" }}>{actions}</div> : null}
          </div>
        </header>

        {filters ? (
          <section
            style={{
              position: "sticky",
              top: "16px",
              zIndex: 5,
              borderRadius: "var(--prs-radius-lg)",
              background: "rgba(255, 255, 255, 0.88)",
              border: "1px solid var(--prs-line)",
              boxShadow: "0 18px 40px rgba(17, 32, 21, 0.08)",
              backdropFilter: "blur(14px)",
              padding: "18px 20px",
            }}
          >
            {filters}
          </section>
        ) : null}

        <section style={{ display: "grid", gap: "20px" }}>{children}</section>
      </section>
    </main>
  );
}
