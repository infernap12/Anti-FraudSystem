package antifraud.user;

public record UserDeleteResponse(String username, String status) {
    UserDeleteResponse(String username) {
        this(username, "Deleted successfully!");
    }
}
