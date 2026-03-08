import { render, screen } from "@testing-library/react";
import { describe, expect, it } from "vitest";
import { FeedbackTriagePage } from "./FeedbackTriagePage";

describe("FeedbackTriagePage", () => {
  it("shows triage queue items before formal work starts", () => {
    render(<FeedbackTriagePage />);

    expect(screen.getByRole("heading", { name: "Feedback Triage" })).toBeInTheDocument();
    expect(screen.getByText("Lobby ficus leaf browning")).toBeInTheDocument();
    expect(screen.getByRole("button", { name: "Review Complaint" })).toBeInTheDocument();
  });
});
