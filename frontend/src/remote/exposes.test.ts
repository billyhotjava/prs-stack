import { describe, expect, it } from "vitest";
import * as remoteExports from "./exposes";

describe("remote exposes", () => {
  it("declares federation modules and DTS route metadata", () => {
    expect(remoteExports.exposes).toEqual({
      "./Workbench": "./src/pages/mobile/MobileWorkbenchPage.tsx",
      "./CustomerPortalHome": "./src/pages/customer/CustomerPortalHomePage.tsx",
      "./ExecutiveCockpit": "./src/pages/executive/OperatingCockpitPage.tsx",
    });

    expect(remoteExports.remoteModuleCatalog).toEqual([
      {
        name: "./Workbench",
        routePath: "/prs/workbench",
        menuLabel: "Field Workbench",
      },
      {
        name: "./CustomerPortalHome",
        routePath: "/prs/customer",
        menuLabel: "Customer Portal",
      },
      {
        name: "./ExecutiveCockpit",
        routePath: "/prs/executive",
        menuLabel: "Executive Cockpit",
      },
    ]);
  });
});
