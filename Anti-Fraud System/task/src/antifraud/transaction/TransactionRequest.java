package antifraud.transaction;



public record TransactionRequest(
//        todo Migrate to hibernate bean validation
//        @Min(0)
        long amount
) {
}
