import { render, screen } from "@testing-library/react";
import { describe, expect, it } from "vitest";
import { ServiceHistoryPage } from "./ServiceHistoryPage";

describe("ServiceHistoryPage", () => {
  it("shows customer-facing service history entries", () => {
    render(<ServiceHistoryPage />);

    expect(screen.getByRole("heading", { name: "Service History" })).toBeInTheDocument();
    expect(screen.getByText("Watering completed and plant condition verified")).toBeInTheDocument();
    expect(screen.getByRole("button", { name: "Request Follow-up" })).toBeInTheDocument();
  });
});
