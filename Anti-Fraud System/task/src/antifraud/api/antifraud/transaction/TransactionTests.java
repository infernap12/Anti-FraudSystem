package antifraud.api.antifraud.transaction;

public enum TransactionTests {
    AMOUNT,
    CARD_NUMBER,
    IP,
    IP_CORRELATION,
    REGION_CORRELATION;

    @Override
    public String toString() {
        return name().toLowerCase().replace('_', '-');
    }
}
