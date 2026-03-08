import { render, screen } from "@testing-library/react";
import { describe, expect, it } from "vitest";
import { ReconciliationWorkbenchPage } from "./ReconciliationWorkbenchPage";

describe("ReconciliationWorkbenchPage", () => {
  it("shows reconciliation differences with project attribution", () => {
    render(<ReconciliationWorkbenchPage />);

    expect(screen.getByRole("heading", { name: "Reconciliation Workbench" })).toBeInTheDocument();
    expect(screen.getByText("PRJ-001")).toBeInTheDocument();
    expect(screen.getByText("Amount mismatch")).toBeInTheDocument();
  });
});
