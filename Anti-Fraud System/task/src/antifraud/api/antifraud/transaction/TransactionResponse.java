package antifraud.api.antifraud.transaction;

import lombok.val;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public record TransactionResponse(String result, String info) {

    public static TransactionResponse fromTestMap(EnumMap<TransactionTests, TransactionVerdict> resultSet) {
        String info;
        TransactionVerdict verdict;
        if (resultSet.containsValue(TransactionVerdict.PROHIBITED)) {
            verdict = TransactionVerdict.PROHIBITED;
            info = getInfoString(resultSet, verdict);
        } else if (resultSet.containsValue(TransactionVerdict.MANUAL_PROCESSING)) {
            verdict = TransactionVerdict.MANUAL_PROCESSING;
            info = getInfoString(resultSet, verdict);
        } else {
            verdict = TransactionVerdict.ALLOWED;
            info = "none";
        }
        return new TransactionResponse(verdict.name(), info);
    }

    private static String getInfoString(EnumMap<TransactionTests, TransactionVerdict> resultSet, TransactionVerdict verdict) {
        return resultSet.entrySet().stream()
                .filter(entry -> entry.getValue() == verdict)
                .map(Map.Entry::getKey)
                .map(TransactionTests::toString)
                .sorted()
                .collect(Collectors.joining(", "));
    }
}
