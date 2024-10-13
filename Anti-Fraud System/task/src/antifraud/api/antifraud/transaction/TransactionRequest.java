package antifraud.api.antifraud.transaction;


import jakarta.validation.constraints.Min;

public record TransactionRequest(

        @Min(1)
        long amount,
        String ip,
        String number
) {
}
