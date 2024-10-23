package antifraud.api.antifraud.transaction;

import lombok.val;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public record TransactionResponse(String result, String info) {

    public static TransactionResponse fromTestMap(EnumMap<TransactionTests, TransactionVerdict> resultSet) {
        TransactionVerdict verdict = TransactionProcessor.max(resultSet);
        String info = verdict != TransactionVerdict.ALLOWED ? getInfoString(resultSet) : "none";
        return new TransactionResponse(verdict.name(), info);
    }

    private static String getInfoString(EnumMap<TransactionTests, TransactionVerdict> resultSet) {
        TransactionVerdict verdict = TransactionProcessor.max(resultSet);
        return resultSet.entrySet().stream()
                .filter(entry -> entry.getValue() == verdict)
                .map(Map.Entry::getKey)
                .map(TransactionTests::toString)
                .sorted()
                .collect(Collectors.joining(", "));
    }
}
