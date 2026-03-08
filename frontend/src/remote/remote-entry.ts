import { CustomerPortalHomePage } from "../pages/customer/CustomerPortalHomePage";
import { ExecutiveCockpitPage } from "../pages/executive/ExecutiveCockpitPage";
import { MobileWorkbenchPage } from "../pages/mobile/MobileWorkbenchPage";

export const remoteModuleCatalog = [
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
] as const;

export { MobileWorkbenchPage as Workbench };
export { CustomerPortalHomePage as CustomerPortalHome };
export { ExecutiveCockpitPage as ExecutiveCockpit };
