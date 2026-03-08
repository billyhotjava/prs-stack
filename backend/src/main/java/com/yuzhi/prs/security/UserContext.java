package com.yuzhi.prs.security;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

public record UserContext(
    String userId,
    Set<String> roles
) {

    public UserContext {
        roles = roles == null
            ? Set.of()
            : roles.stream()
                .map(RoleCatalog::normalize)
                .filter(RoleCatalog.ALL::contains)
                .collect(Collectors.toCollection(LinkedHashSet::new));
        roles = Set.copyOf(roles);
    }

    public boolean hasRole(String role) {
        return roles.contains(RoleCatalog.normalize(role));
    }
}
