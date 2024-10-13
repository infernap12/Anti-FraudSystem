package antifraud.api.antifraud.suspiciousIp;

import org.springframework.data.jpa.repository.JpaRepository;

import java.net.InetAddress;
import java.util.Optional;

public interface IpEntityRepository extends JpaRepository<IpEntity, Long> {
    boolean existsByIp(InetAddress ip);

    Optional<IpEntity> findByIp(InetAddress ip);
}
