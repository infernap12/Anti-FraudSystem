package antifraud.limit;


import antifraud.api.antifraud.transaction.Transaction;
import antifraud.api.antifraud.transaction.TransactionVerdict;
import lombok.val;
import org.apache.commons.lang3.mutable.MutableDouble;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import static antifraud.api.antifraud.transaction.TransactionVerdict.*;

@Service
public class LimitService {
    public static final int DEFAULT_ALLOWED_LIMIT = 200;
    public static final int DEFAULT_MANUAL_LIMIT = 1500;

    private final LimitRepository limitRepository;

    public LimitService(LimitRepository limitRepository) {
        this.limitRepository = limitRepository;
        if (!limitRepository.existsByName(ALLOWED)) {
            limitRepository.save(new Limit(ALLOWED, DEFAULT_ALLOWED_LIMIT));
        }
        if (!limitRepository.existsByName(MANUAL_PROCESSING)) {
            limitRepository.save(new Limit(MANUAL_PROCESSING, DEFAULT_MANUAL_LIMIT));
        }
    }

    public Limit getLimit(TransactionVerdict limitName) {
        return limitRepository.findByName(limitName);
    }

    private void mutateLimit(boolean isIncrement, Transaction transaction, TransactionVerdict limitToUpdate) {
        val entity = getLimit(limitToUpdate);
        final MutableDouble mutableDouble = new MutableDouble(0.8 * entity.getValue());
        Consumer<Double> op = isIncrement ? mutableDouble::add : mutableDouble::subtract;
        op.accept(0.2 * transaction.getAmount());

        entity.setValue((int) Math.ceil(mutableDouble.doubleValue()));
        val saved = limitRepository.save(entity);
        System.out.println(saved);
    }

    public void updateLimits(Transaction transaction) {
        int i = (transaction.getResult().i - transaction.getFeedback().i);
        List<TransactionVerdict> verdicts = new ArrayList<>();
        if ((Math.abs(i) & 1) == 1) {
            verdicts.add(ALLOWED);
        }
        if ((Math.abs(i) & 2) == 2) {
            verdicts.add(MANUAL_PROCESSING);
        }
        for (TransactionVerdict verdict : verdicts) {
            mutateLimit(Math.abs(i) == i, transaction, verdict);
        }
    }
}
