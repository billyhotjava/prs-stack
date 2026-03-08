import { render, screen } from "@testing-library/react";
import { describe, expect, it } from "vitest";
import { PlantChangeDraftPage } from "./PlantChangeDraftPage";

describe("PlantChangeDraftPage", () => {
  it("shows multimodal intake and supervisor approval action", () => {
    render(<PlantChangeDraftPage />);

    expect(screen.getByRole("heading", { name: "Plant Change Draft" })).toBeInTheDocument();
    expect(screen.getByText("Photo Intake")).toBeInTheDocument();
    expect(screen.getByText("Voice Notes")).toBeInTheDocument();
    expect(screen.getByRole("button", { name: "Submit For Supervisor Approval" })).toBeInTheDocument();
  });
});
