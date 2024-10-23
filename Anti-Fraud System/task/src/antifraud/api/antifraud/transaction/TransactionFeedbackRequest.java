package antifraud.api.antifraud.transaction;

import java.io.Serializable;

/**
 * Feedback for {@link Transaction}
 */
public record TransactionFeedbackRequest(long transactionId, TransactionVerdict feedback) implements Serializable {
}
