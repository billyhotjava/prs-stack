export const exposes = {
  "./Workbench": "./src/pages/mobile/MobileWorkbenchPage.tsx",
  "./CustomerPortalHome": "./src/pages/customer/CustomerPortalHomePage.tsx",
  "./ExecutiveCockpit": "./src/pages/executive/OperatingCockpitPage.tsx",
} as const;

export { remoteModuleCatalog } from "./remote-entry";
