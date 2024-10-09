package antifraud.auth;


public enum Authority {
    READ_USER,
    WRITE_USER,
    EXECUTE_TRANSACTION;

    public String getAuthority() {
        return name();
    }
}
