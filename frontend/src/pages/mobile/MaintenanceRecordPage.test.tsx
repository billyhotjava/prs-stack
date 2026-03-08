import { render, screen } from "@testing-library/react";
import { describe, expect, it } from "vitest";
import { MaintenanceRecordPage } from "./MaintenanceRecordPage";

describe("MaintenanceRecordPage", () => {
  it("shows service completion cards and customer-ready feed entries", () => {
    render(<MaintenanceRecordPage />);

    expect(screen.getByRole("heading", { name: "Service Feed" })).toBeInTheDocument();
    expect(screen.getByText("Watering completed and plant condition verified")).toBeInTheDocument();
    expect(screen.getByRole("button", { name: "Publish To Customer" })).toBeInTheDocument();
  });
});
