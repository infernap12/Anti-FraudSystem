package antifraud.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import us.fatehi.creditcardnumber.AccountNumbers;

public class CardNumberValidator implements ConstraintValidator<CardNumber, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return isValid(value);
    }

    public boolean isValid(String value) {
        return AccountNumbers.completeAccountNumber(value).isPrimaryAccountNumberValid();
    }
}