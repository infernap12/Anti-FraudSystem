package antifraud.api.antifraud.transaction;

import antifraud.limit.LimitService;
import antifraud.validation.CardNumber;
import lombok.val;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.EnumMap;
import java.util.List;

@Service
public class TransactionService {
    private final TransactionRepository repo;
    private final TransactionProcessor processor;
    private final LimitService limitService;

    public TransactionService(TransactionRepository repo, TransactionProcessor processor, LimitService limitService) {
        this.repo = repo;
        this.processor = processor;
        this.limitService = limitService;
    }


    public EnumMap<TransactionTests, TransactionVerdict> validate(Transaction transaction) {
        val resultSet = processor.validate(transaction);
        val verdict = TransactionProcessor.max(resultSet);
        transaction.setResult(verdict);
        saveTransaction(transaction);
        return resultSet;
    }

    public Transaction saveTransaction(Transaction transaction) {
        return repo.save(transaction);
    }

    public Transaction submitFeedback(Long transactionId, TransactionVerdict feedback) {
        val transaction = repo.findById(transactionId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "What the hell man"));
        if (transaction.getFeedback() != null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
        if (transaction.getResult() == feedback) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY);
        }
        transaction.setFeedback(feedback);
        val saved = repo.save(transaction);
        limitService.updateLimits(saved);
        return saved;
    }

    public List<Transaction> getAll() {
        return repo.findAll(Sort.by(Sort.Order.asc("id")));
    }

    public List<Transaction> getAllForNumber(@CardNumber String number) {
        return repo.findByNumber(number);
    }
}
