package antifraud.auth;


public enum Authority {
    READ_USER,
    WRITE_USER,
    EXECUTE_TRANSACTION,
    STOLEN_CARD,
    SUSPICIOUS_IP;

    public String getAuthority() {
        return name();
    }
}
