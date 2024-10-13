package antifraud.api.antifraud.transaction;

import lombok.val;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public record TransactionResponse(String result, String info) {
    public static TransactionResponse fromVerdict(TransactionVerdict verdict, List<String> reasons) {
        final String info;
        if (reasons.isEmpty()) {
            info = "none";
        } else {
            info = reasons.stream().sorted().collect(Collectors.joining(", "));
        }
        return new TransactionResponse(verdict.name(), info);
    }

    public static TransactionResponse fromVerdict(TransactionVerdict amountVerdict, boolean isCard, boolean isIp) {
        List<String> reasons = new ArrayList<>();
        final TransactionVerdict verdict;
        val isAmount = amountVerdict == TransactionVerdict.PROHIBITED;
        if (isCard || isIp || isAmount) {
            verdict = TransactionVerdict.PROHIBITED;
            if (isCard) {
                reasons.add("card-number");
            }
            if (isIp) {
                reasons.add("ip");
            }
            if (isAmount) {
                reasons.add("amount");
            }
        } else if (amountVerdict == TransactionVerdict.MANUAL_PROCESSING) {
            reasons.add("amount");
            verdict = TransactionVerdict.MANUAL_PROCESSING;
        } else {
            reasons.add("none");
            verdict = TransactionVerdict.ALLOWED;
        }
        return fromVerdict(verdict, reasons);
    }
}
