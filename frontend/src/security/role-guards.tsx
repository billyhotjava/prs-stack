import type { PropsWithChildren, ReactNode } from "react";

export type PrsRole =
  | "operations"
  | "procurement"
  | "finance"
  | "supervision"
  | "maintenance"
  | "department-supervisor"
  | "customer";

export function hasAnyRole(userRoles: readonly PrsRole[], requiredRoles: readonly PrsRole[]) {
  return requiredRoles.some((role) => userRoles.includes(role));
}

type RoleGuardProps = PropsWithChildren<{
  userRoles: readonly PrsRole[];
  requiredRoles: readonly PrsRole[];
  fallback?: ReactNode;
}>;

export function RoleGuard({ userRoles, requiredRoles, fallback = null, children }: RoleGuardProps) {
  return hasAnyRole(userRoles, requiredRoles) ? children : <>{fallback}</>;
}
