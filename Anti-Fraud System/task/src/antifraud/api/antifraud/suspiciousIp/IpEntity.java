package antifraud.api.antifraud.suspiciousIp;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.net.InetAddress;

@Setter
@Getter
@Entity
@ToString
@RequiredArgsConstructor
@Table(name = "SUSPICIOUS_IPS")
public class IpEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private InetAddress ip;

    public IpEntity(InetAddress ip) {
        this.ip = ip;
    }
}
