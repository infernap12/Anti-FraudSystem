package antifraud.user;

import antifraud.auth.UserRole;

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
