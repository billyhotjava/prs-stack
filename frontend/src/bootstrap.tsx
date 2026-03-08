import React from "react";
import ReactDOM from "react-dom/client";
import { MobileWorkbenchPage } from "./pages/mobile/MobileWorkbenchPage";

export function bootstrap() {
  const rootElement = document.getElementById("root");
  if (!rootElement) {
    throw new Error("Root element #root was not found");
  }

  ReactDOM.createRoot(rootElement).render(
    <React.StrictMode>
      <MobileWorkbenchPage />
    </React.StrictMode>,
  );
}
