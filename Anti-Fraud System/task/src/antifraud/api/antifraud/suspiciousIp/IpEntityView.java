package antifraud.api.antifraud.suspiciousIp;

import java.io.Serializable;
import java.net.InetAddress;

/**
 * DTO for {@link IpEntity}
 */
public record IpEntityView(Long id, InetAddress ip) implements Serializable {
    public IpEntityView(IpEntity ipEntity) {
        this(ipEntity.getId(), ipEntity.getIp());
    }
}