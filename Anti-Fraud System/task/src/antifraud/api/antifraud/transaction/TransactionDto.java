package antifraud.api.antifraud.transaction;

import antifraud.validation.CardNumber;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.io.Serializable;
import java.util.Optional;

/**
 * DTO for {@link Transaction}
 */
public record TransactionDto(
        long transactionId,
        @Min(1) long amount,
        @NotEmpty String ip,
        @CardNumber String number,
        @NotBlank String region,
        String date,
        String result,
        String feedback
) implements Serializable {
    public static TransactionDto fromTransaction(Transaction transaction) {
        final String feedback = Optional.of(transaction.getFeedback()).map(TransactionVerdict::toString).orElse("");
        return new TransactionDto(
                transaction.getId(),
                transaction.getAmount(),
                transaction.getIp(),
                transaction.getNumber(),
                transaction.getRegion().toString(),
                transaction.getDate().toString(),
                transaction.getResult().toString(),
                feedback
        );
    }
}