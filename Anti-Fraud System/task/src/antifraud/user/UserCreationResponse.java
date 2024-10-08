package antifraud.user;

public record UserCreationResponse(
        long id,
        String name,
        String username
) {
    public UserCreationResponse(UserEntity user) {
        this(user.getId(), user.getName(), user.getUsername());
    }
}
