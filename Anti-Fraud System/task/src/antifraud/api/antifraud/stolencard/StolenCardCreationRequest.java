package antifraud.api.antifraud.stolencard;

import org.hibernate.validator.constraints.CreditCardNumber;

import java.io.Serializable;

/**
 Creation request for {@link StolenCard}
 */
public record StolenCardCreationRequest(
        @CreditCardNumber String number
) implements Serializable {
}
