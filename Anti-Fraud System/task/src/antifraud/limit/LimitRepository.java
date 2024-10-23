package antifraud.limit;

import antifraud.api.antifraud.transaction.TransactionVerdict;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface LimitRepository extends JpaRepository<Limit, Long> {
    Limit findByName(TransactionVerdict name);


    boolean existsByName(TransactionVerdict name);
}