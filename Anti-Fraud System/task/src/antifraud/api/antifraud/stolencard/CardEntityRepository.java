package antifraud.api.antifraud.stolencard;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CardEntityRepository extends JpaRepository<CardEntity, Long> {
    Optional<CardEntity> findByNumber(String number);

    boolean existsByNumber(String accountNumber);
}