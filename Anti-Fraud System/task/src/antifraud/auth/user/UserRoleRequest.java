package antifraud.auth.user;

import antifraud.auth.UserRole;

public record UserRoleRequest(String username, UserRole role) {
}
