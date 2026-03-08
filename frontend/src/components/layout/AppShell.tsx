import type { PropsWithChildren, ReactNode } from "react";

type AppShellProps = PropsWithChildren<{
  eyebrow: string;
  title: string;
  description: string;
  heroAction?: ReactNode;
}>;

export function AppShell({ eyebrow, title, description, heroAction, children }: AppShellProps) {
  return (
    <main style={{ padding: "24px 16px 40px" }}>
      <section
        style={{
          margin: "0 auto",
          maxWidth: "960px",
          border: "1px solid var(--prs-line)",
          borderRadius: "var(--prs-radius-xl)",
          background: "var(--prs-surface)",
          backdropFilter: "blur(18px)",
          boxShadow: "var(--prs-shadow)",
          overflow: "hidden",
        }}
      >
        <header
          style={{
            padding: "28px 24px 20px",
            borderBottom: "1px solid var(--prs-line)",
            background:
              "linear-gradient(135deg, rgba(29, 122, 67, 0.12), rgba(216, 239, 149, 0.4))",
          }}
        >
          <div style={{ color: "var(--prs-primary-strong)", fontSize: "0.85rem", fontWeight: 700, letterSpacing: "0.08em", textTransform: "uppercase" }}>
            {eyebrow}
          </div>
          <div
            style={{
              display: "grid",
              gap: "16px",
              alignItems: "end",
              marginTop: "12px",
              gridTemplateColumns: "repeat(auto-fit, minmax(240px, 1fr))",
            }}
          >
            <div>
              <h1 style={{ margin: 0, fontSize: "clamp(2rem, 4vw, 3.4rem)", lineHeight: 1.05 }}>
                {title}
              </h1>
              <p style={{ margin: "12px 0 0", maxWidth: "560px", color: "var(--prs-text-muted)", fontSize: "1rem", lineHeight: 1.6 }}>
                {description}
              </p>
            </div>
            <div style={{ justifySelf: "start" }}>{heroAction}</div>
          </div>
        </header>
        <div style={{ padding: "24px", display: "grid", gap: "20px" }}>{children}</div>
      </section>
    </main>
  );
}
