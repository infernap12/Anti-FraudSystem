package antifraud.api.antifraud.transaction;

import java.util.Arrays;
import java.util.Collection;

public enum TransactionVerdict {
    ALLOWED(1),
    MANUAL_PROCESSING(2),
    PROHIBITED(4);

    public final int i;

    TransactionVerdict(int i) {
        this.i = i;
    }

    TransactionVerdict ofI(int i) {
        return Arrays.stream(values()).filter((it) -> it.i == i).findFirst().get();
    }

//    Collection<TransactionVerdict> fromI(int i) {
//
//    }
}