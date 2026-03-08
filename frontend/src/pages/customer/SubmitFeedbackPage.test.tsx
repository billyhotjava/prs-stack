import { render, screen } from "@testing-library/react";
import { describe, expect, it } from "vitest";
import { SubmitFeedbackPage } from "./SubmitFeedbackPage";

describe("SubmitFeedbackPage", () => {
  it("shows customer request types and routes them to operations review", () => {
    render(<SubmitFeedbackPage />);

    expect(screen.getByRole("heading", { name: "Submit Feedback" })).toBeInTheDocument();
    expect(screen.getByText("Complaint")).toBeInTheDocument();
    expect(screen.getByRole("button", { name: "Send To Operations Review" })).toBeInTheDocument();
  });
});
