package antifraud.api.auth;

import lombok.Getter;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.Set;

import static antifraud.api.auth.Authority.*;

@Getter
public enum UserRole {
    ADMINISTRATOR(READ_USER, WRITE_USER),
    MERCHANT(EXECUTE_TRANSACTION),
    SUPPORT(READ_USER,
            STOLEN_CARD,
            SUSPICIOUS_IP,
            READ_TRANSACTION,
            WRITE_TRANSACTION);

    private final Set<Authority> authorities;

    UserRole(Authority... authorities) {
        this.authorities = EnumSet.copyOf(Arrays.asList(authorities));
    }

    public String getRole() {
        return "ROLE_" + name();
    }
}
