import { render, screen } from "@testing-library/react";
import { describe, expect, it } from "vitest";
import { ReceivablesPage } from "./ReceivablesPage";

describe("ReceivablesPage", () => {
  it("shows invoice and collection business facts", () => {
    render(<ReceivablesPage />);

    expect(screen.getByRole("heading", { name: "Receivables & Payables" })).toBeInTheDocument();
    expect(screen.getByText("FP-2026-001")).toBeInTheDocument();
    expect(screen.getByText("1,200.00 / 800.00")).toBeInTheDocument();
  });
});
