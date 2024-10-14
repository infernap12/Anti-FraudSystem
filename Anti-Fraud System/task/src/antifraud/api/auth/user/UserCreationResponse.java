package antifraud.api.auth.user;

import antifraud.api.auth.UserRole;

/**
 * DTO for {@link UserEntity}
 */
public record UserCreationResponse(
        long id,
        String name,
        String username,
        UserRole role
) {
    public UserCreationResponse(UserEntity user) {
        this(user.getId(), user.getName(), user.getUsername(), user.getRole());
    }
}
