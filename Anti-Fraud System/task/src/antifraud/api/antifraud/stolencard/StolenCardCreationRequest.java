package antifraud.api.antifraud.stolencard;

import antifraud.validation.CardNumber;
import org.hibernate.validator.constraints.CreditCardNumber;

import java.io.Serializable;

/**
 Creation request for {@link StolenCard}
 */
public record StolenCardCreationRequest(
        @CardNumber String number
) implements Serializable {
}
