const tree = [
  {
    building: "Tower A",
    floors: [
      {
        floor: "18F",
        positions: ["Reception East", "VIP Lounge", "North Corridor"],
      },
      {
        floor: "22F",
        positions: ["Executive Lobby", "Tea Bar"],
      },
    ],
  },
];

export function PositionTreePage() {
  return (
    <article
      style={{
        borderRadius: "var(--prs-radius-lg)",
        border: "1px solid var(--prs-line)",
        background: "rgba(248, 251, 245, 0.92)",
        padding: "18px",
      }}
    >
      <h3 style={{ margin: 0, fontSize: "1rem" }}>Position Structure</h3>
      <div style={{ marginTop: "16px", display: "grid", gap: "14px" }}>
        {tree.map((building) => (
          <section key={building.building} style={{ display: "grid", gap: "12px" }}>
            <div style={{ fontWeight: 700 }}>{building.building}</div>
            {building.floors.map((floor) => (
              <div key={floor.floor} style={{ paddingLeft: "14px", display: "grid", gap: "8px" }}>
                <div style={{ color: "var(--prs-text-muted)", fontWeight: 600 }}>{floor.floor}</div>
                <div style={{ display: "grid", gap: "8px" }}>
                  {floor.positions.map((position) => (
                    <div
                      key={position}
                      style={{
                        padding: "10px 12px",
                        borderRadius: "14px",
                        background: "#fff",
                        border: "1px solid var(--prs-line)",
                      }}
                    >
                      {position}
                    </div>
                  ))}
                </div>
              </div>
            ))}
          </section>
        ))}
      </div>
    </article>
  );
}
