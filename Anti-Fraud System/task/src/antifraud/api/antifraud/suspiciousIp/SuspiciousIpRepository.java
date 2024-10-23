package antifraud.api.antifraud.suspiciousIp;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface SuspiciousIpRepository extends JpaRepository<SuspiciousIp, Long> {
    boolean existsByIp(String ip);

    Optional<SuspiciousIp> findByIp(String ip);

}
