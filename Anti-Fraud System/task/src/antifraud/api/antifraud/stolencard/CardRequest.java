package antifraud.api.antifraud.stolencard;

import org.hibernate.validator.constraints.CreditCardNumber;

import java.io.Serializable;

/**
 Creation request for {@link CardEntity}
 */
public record CardRequest(
        @CreditCardNumber String number
) implements Serializable {
}
