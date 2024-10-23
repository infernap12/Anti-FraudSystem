package antifraud.api.antifraud.transaction;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    // todo Be aware that we need to test transaction for a given account number.
    // logic may be off on this, sniff the sql that is produced
    @Query("select count(distinct t.region) from Transaction t where t.date between ?1 and ?2 and t.number = ?3")
    int countDistinctRegionByDateBetweenAndNumber(LocalDateTime date, LocalDateTime date2, String number);

    @Query("select count(distinct t.ip) from Transaction t where t.date between ?1 and ?2 and t.number = ?3")
    int countDistinctIpByDateBetweenAndNumber(LocalDateTime date, LocalDateTime date2, String number);

    List<Transaction> findByNumber(String number);


}