import { render, screen } from "@testing-library/react";
import { describe, expect, it } from "vitest";
import { MyTasksPage } from "./MyTasksPage";

describe("MyTasksPage", () => {
  it("shows the field inbox with touch-first task actions", () => {
    render(<MyTasksPage />);

    expect(screen.getByRole("heading", { name: "My Tasks" })).toBeInTheDocument();
    expect(screen.getByRole("heading", { name: "Arrival Check-in" })).toBeInTheDocument();
    expect(screen.getByRole("button", { name: "Open Task Detail" })).toBeInTheDocument();
    expect(screen.getByRole("button", { name: "Arrived On Site" })).toBeInTheDocument();
  });
});
