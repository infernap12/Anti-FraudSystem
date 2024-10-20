package antifraud.api.antifraud.transaction;

import org.springframework.stereotype.Service;

import java.util.EnumMap;

@Service
public class TransactionService {
    private final TransactionRepository repo;
    private final TransactionProcessor processor;

    public TransactionService(TransactionRepository repo, TransactionProcessor processor) {
        this.repo = repo;
        this.processor = processor;
    }


    public EnumMap<TransactionTests, TransactionVerdict> validate(Transaction transaction) {
        return processor.validate(transaction);
    }

    public Transaction saveTransaction(Transaction transaction) {
        return repo.save(transaction);
    }
}
