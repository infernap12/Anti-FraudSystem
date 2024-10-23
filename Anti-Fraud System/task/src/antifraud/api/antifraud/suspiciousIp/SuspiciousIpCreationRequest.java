package antifraud.api.antifraud.suspiciousIp;

import antifraud.validation.IpAddress;
import org.springframework.validation.annotation.Validated;

import java.io.Serializable;

/**
 Creation request for {@link SuspiciousIp}
 */
@Validated
public record SuspiciousIpCreationRequest(
        @IpAddress String ip
) implements Serializable {
}
