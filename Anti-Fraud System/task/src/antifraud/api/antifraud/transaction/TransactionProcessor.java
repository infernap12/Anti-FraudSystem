package antifraud.api.antifraud.transaction;

import antifraud.api.antifraud.stolencard.StolenCardRepository;
import antifraud.api.antifraud.suspiciousIp.SuspiciousIpRepository;
import antifraud.limit.LimitService;
import lombok.val;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.EnumMap;

@Component
public class TransactionProcessor {
    private final StolenCardRepository stolenCardRepository;
    private final SuspiciousIpRepository suspiciousIpRepository;
    private final TransactionRepository transactionRepository;
    private final LimitService limitService;

    public TransactionProcessor(StolenCardRepository stolenCardRepository, SuspiciousIpRepository suspiciousIpRepository, TransactionRepository transactionRepository, LimitService limitService) {
        this.stolenCardRepository = stolenCardRepository;
        this.suspiciousIpRepository = suspiciousIpRepository;
        this.transactionRepository = transactionRepository;
        this.limitService = limitService;
    }

    public EnumMap<TransactionTests, TransactionVerdict> validate(Transaction transaction) {
        EnumMap<TransactionTests, TransactionVerdict> verdictMap = new EnumMap<>(TransactionTests.class);
        verdictMap.put(TransactionTests.AMOUNT, amount(transaction));
        verdictMap.put(TransactionTests.CARD_NUMBER, stolenCard(transaction));
        verdictMap.put(TransactionTests.IP, suspiciousIp(transaction));
        verdictMap.put(TransactionTests.IP_CORRELATION, correlateIp(transaction));
        verdictMap.put(TransactionTests.REGION_CORRELATION, correlateRegion(transaction));
        return verdictMap;
    }

    private TransactionVerdict correlateRegion(Transaction transaction) {
        LocalDateTime start;
        LocalDateTime end = transaction.getDate();
        start = end.minusHours(1);
        int count = transactionRepository.countDistinctRegionByDateBetweenAndNumber(start, end, transaction.getNumber());
        if (count > 3) {
            return TransactionVerdict.PROHIBITED;
        } else if (count == 3) {
            return TransactionVerdict.MANUAL_PROCESSING;
        } else return TransactionVerdict.ALLOWED;
    }

    private TransactionVerdict correlateIp(Transaction transaction) {
        LocalDateTime start;
        LocalDateTime end = transaction.getDate();
        start = end.minusHours(1);
        int count = transactionRepository.countDistinctIpByDateBetweenAndNumber(start, end, transaction.getNumber());
        if (count > 3) {
            return TransactionVerdict.PROHIBITED;
        } else if (count == 3) {
            return TransactionVerdict.MANUAL_PROCESSING;
        } else return TransactionVerdict.ALLOWED;
    }

    private TransactionVerdict suspiciousIp(Transaction transaction) {
        if (suspiciousIpRepository.existsByIp(transaction.getIp())) {
            return TransactionVerdict.PROHIBITED;
        } else return TransactionVerdict.ALLOWED;
    }

    private TransactionVerdict stolenCard(Transaction transaction) {
        if (stolenCardRepository.existsByNumber(transaction.getNumber())) {
            return TransactionVerdict.PROHIBITED;
        } else return TransactionVerdict.ALLOWED;
    }

    private TransactionVerdict amount(Transaction transaction) {
        if (transaction.getAmount() > limitService.getLimit(TransactionVerdict.MANUAL_PROCESSING).getValue()) {
            return TransactionVerdict.PROHIBITED;
        } else if (transaction.getAmount() > limitService.getLimit(TransactionVerdict.ALLOWED).getValue()) {
            return TransactionVerdict.MANUAL_PROCESSING;
        } else return TransactionVerdict.ALLOWED;
    }

    public static TransactionVerdict max(EnumMap<TransactionTests, TransactionVerdict> verdictEnumMap) {
        return verdictEnumMap.values().stream().max(Comparator.comparingInt(Enum::ordinal)).get();
    }
}
