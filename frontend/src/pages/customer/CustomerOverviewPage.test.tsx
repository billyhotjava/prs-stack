import { render, screen } from "@testing-library/react";
import { describe, expect, it } from "vitest";
import { CustomerOverviewPage } from "./CustomerOverviewPage";

describe("CustomerOverviewPage", () => {
  it("shows project visibility cards for customer users", () => {
    render(<CustomerOverviewPage />);

    expect(screen.getByRole("heading", { name: "Project Overview" })).toBeInTheDocument();
    expect(screen.getByText("Active projects")).toBeInTheDocument();
    expect(screen.getByText("Shanghai IFC Tower")).toBeInTheDocument();
  });
});
