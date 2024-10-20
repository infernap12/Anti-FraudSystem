package antifraud.api.antifraud.suspiciousIp;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@Entity
@ToString
@RequiredArgsConstructor
@Table(name = "SUSPICIOUS_IPS")
public class SuspiciousIp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private String ip;

    public SuspiciousIp(String ip) {
        this.ip = ip;
    }
}
