package antifraud.api.auth.user;

public record UserDeleteResponse(String username, String status) {
    public UserDeleteResponse(String username) {
        this(username, "Deleted successfully!");
    }
}
