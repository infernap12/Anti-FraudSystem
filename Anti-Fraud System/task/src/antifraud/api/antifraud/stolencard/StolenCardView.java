package antifraud.api.antifraud.stolencard;

import java.io.Serializable;

/**
 Creation view for {@link StolenCard}
 */
public record StolenCardView(
        long id,
        String number
) implements Serializable {
    public StolenCardView(StolenCard stolenCard) {
        this(stolenCard.getId(), stolenCard.getNumber());
    }
}
