import { render, screen } from "@testing-library/react";
import { describe, expect, it } from "vitest";
import { OperatingCockpitPage } from "./OperatingCockpitPage";

describe("OperatingCockpitPage", () => {
  it("shows executive KPIs, anomaly timeline, and AI summary", () => {
    render(<OperatingCockpitPage />);

    expect(screen.getByRole("heading", { name: "Operating Cockpit" })).toBeInTheDocument();
    expect(screen.getByText("Reconciliation risk")).toBeInTheDocument();
    expect(screen.getByText("AI operating brief")).toBeInTheDocument();
  });
});
