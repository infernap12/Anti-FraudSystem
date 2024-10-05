package antifraud.transaction;

public record TransactionResponse(String result) {
    public static TransactionResponse fromVerdict(TransactionVerdict verdict) {
        return new TransactionResponse(verdict.name());
    }
}
