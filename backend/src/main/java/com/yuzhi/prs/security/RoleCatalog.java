package com.yuzhi.prs.security;

import java.util.Locale;
import java.util.Set;

public final class RoleCatalog {

    public static final String OPERATIONS = "operations";
    public static final String PROCUREMENT = "procurement";
    public static final String FINANCE = "finance";
    public static final String SUPERVISION = "supervision";
    public static final String MAINTENANCE = "maintenance";
    public static final String DEPARTMENT_SUPERVISOR = "department-supervisor";
    public static final String CUSTOMER = "customer";

    public static final Set<String> ALL = Set.of(
        OPERATIONS,
        PROCUREMENT,
        FINANCE,
        SUPERVISION,
        MAINTENANCE,
        DEPARTMENT_SUPERVISOR,
        CUSTOMER
    );

    private RoleCatalog() {}

    public static String normalize(String role) {
        return role == null ? "" : role.trim().toLowerCase(Locale.ROOT);
    }

    public static String authorityFor(String role) {
        return "ROLE_" + normalize(role).replace('-', '_').toUpperCase(Locale.ROOT);
    }
}
