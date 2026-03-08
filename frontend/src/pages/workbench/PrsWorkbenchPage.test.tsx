import { render, screen } from "@testing-library/react";
import { PrsWorkbenchPage } from "./PrsWorkbenchPage";

describe("PrsWorkbenchPage", () => {
  it("shows the mobile-first field workbench heading and primary action", () => {
    render(<PrsWorkbenchPage />);

    expect(screen.getByRole("heading", { name: "Today's Field Work" })).toBeInTheDocument();
    expect(screen.getByRole("button", { name: "Start Inspection" })).toBeInTheDocument();
  });
});
