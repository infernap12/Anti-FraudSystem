package antifraud.api.antifraud.suspiciousIp;

import java.io.Serializable;

/**
 Creation request for {@link IpEntity}
 */
public record IpRequest(
        String ip) implements Serializable {
}
