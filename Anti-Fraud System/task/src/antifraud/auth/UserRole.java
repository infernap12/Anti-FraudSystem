package antifraud.auth;

import lombok.Getter;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.Set;

@Getter
public enum UserRole {
    ADMINISTRATOR(Authority.READ_USER, Authority.WRITE_USER),
    MERCHANT(Authority.EXECUTE_TRANSACTION),
    SUPPORT(Authority.READ_USER, Authority.STOLEN_CARD, Authority.SUSPICIOUS_IP);

    private final Set<Authority> authorities;

    UserRole(Authority... authorities) {
        this.authorities = EnumSet.copyOf(Arrays.asList(authorities));
    }

    public String getRole() {
        return "ROLE_" + name();
    }
}
