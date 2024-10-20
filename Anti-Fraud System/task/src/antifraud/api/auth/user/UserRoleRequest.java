package antifraud.api.auth.user;

import antifraud.api.auth.UserRole;

public record UserRoleRequest(String username, UserRole role) {
}
