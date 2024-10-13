package antifraud.api.antifraud.transaction;

import org.springframework.stereotype.Service;

@Service
public class TransactionService {
    public TransactionVerdict validate(Long amount) {
        if (amount <= 200) {
            return TransactionVerdict.ALLOWED;
        } else if (amount <= 1500) {
            return TransactionVerdict.MANUAL_PROCESSING;
        } else {
            return TransactionVerdict.PROHIBITED;
        }
    }
}
