import { defineConfig } from "vite";
import react from "@vitejs/plugin-react";
import federation from "@originjs/vite-plugin-federation";
import { exposes } from "./src/remote/exposes";

export default defineConfig({
  plugins: [
    react(),
    federation({
      name: "prs",
      filename: "remoteEntry.js",
      exposes,
      shared: ["react", "react-dom"],
    }),
  ],
  build: {
    target: "chrome108",
    modulePreload: false,
  },
  test: {
    environment: "jsdom",
    globals: true,
    setupFiles: "./src/test/setup.ts"
  }
});
