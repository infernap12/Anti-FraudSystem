package antifraud.api.auth;


public enum Authority {
    READ_USER,
    WRITE_USER,
    EXECUTE_TRANSACTION,
    STOLEN_CARD,
    SUSPICIOUS_IP,
    READ_TRANSACTION,
    WRITE_TRANSACTION;

    public String getAuthority() {
        return name();
    }
}
