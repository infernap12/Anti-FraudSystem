package antifraud.api.antifraud.stolencard;

import java.io.Serializable;

/**
 Creation view for {@link CardEntity}
 */
public record CardResponse(
        long id,
        String number
) implements Serializable {
    public CardResponse(CardEntity cardEntity) {
        this(cardEntity.getId(), cardEntity.getNumber());
    }
}
