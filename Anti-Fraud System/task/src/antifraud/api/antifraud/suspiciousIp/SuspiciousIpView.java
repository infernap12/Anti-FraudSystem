package antifraud.api.antifraud.suspiciousIp;

import java.io.Serializable;
import java.net.InetAddress;

/**
 * Creation view for {@link SuspiciousIp}
 */
public record SuspiciousIpView(Long id, String ip) implements Serializable {
    public SuspiciousIpView(SuspiciousIp suspiciousIp) {
        this(suspiciousIp.getId(), suspiciousIp.getIp());
    }
}