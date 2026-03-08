import { render, screen } from "@testing-library/react";
import { describe, expect, it } from "vitest";
import { ProjectListPage } from "./ProjectListPage";

describe("ProjectListPage", () => {
  it("shows a desktop-first master data workspace with filters, table, and detail pane", () => {
    render(<ProjectListPage />);

    expect(screen.getByRole("heading", { name: "Project Master Data" })).toBeInTheDocument();
    expect(screen.getByRole("button", { name: "New Project" })).toBeInTheDocument();
    expect(screen.getByRole("heading", { name: "Shanghai IFC Tower" })).toBeInTheDocument();
    expect(screen.getByText("Position Structure")).toBeInTheDocument();
    expect(screen.getByText("Reception East")).toBeInTheDocument();
  });
});
