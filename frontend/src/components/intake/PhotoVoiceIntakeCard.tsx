export function PhotoVoiceIntakeCard() {
  return (
    <article
      style={{
        borderRadius: "var(--prs-radius-lg)",
        background: "#17381f",
        color: "#f5fff6",
        padding: "20px",
        display: "grid",
        gap: "16px",
      }}
    >
      <div>
        <div style={{ fontSize: "0.88rem", letterSpacing: "0.04em", textTransform: "uppercase", opacity: 0.72 }}>
          AI intake
        </div>
        <h2 style={{ margin: "8px 0 0", fontSize: "1.15rem" }}>Photo &amp; voice intake</h2>
        <p style={{ margin: "12px 0 0", lineHeight: 1.6, color: "rgba(245, 255, 246, 0.78)" }}>
          Capture on-site issues fast and let PRS draft the plant-change request before supervisor review.
        </p>
      </div>
      <div style={{ display: "grid", gap: "10px", gridTemplateColumns: "repeat(2, minmax(0, 1fr))" }}>
        <button
          style={{
            minHeight: "48px",
            borderRadius: "16px",
            background: "#f5fff6",
            color: "#17381f",
            fontWeight: 700,
          }}
          type="button"
        >
          Take Photo
        </button>
        <button
          style={{
            minHeight: "48px",
            borderRadius: "16px",
            background: "rgba(255, 255, 255, 0.14)",
            color: "#f5fff6",
            fontWeight: 700,
            border: "1px solid rgba(255, 255, 255, 0.16)",
          }}
          type="button"
        >
          Record Voice
        </button>
      </div>
    </article>
  );
}
