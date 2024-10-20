package antifraud;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Validator class to check if a given string is a valid IP address.
 * <p>
 * This class implements the `ConstraintValidator` interface,
 * providing custom validation logic for the `@IpAddress` annotation.
 */
public class IpAddressValidator implements ConstraintValidator<IpAddress, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return isValid(value);
    }

    public boolean isValid(String value) {
        try {
            InetAddress.getByName(value);
        } catch (UnknownHostException e) {
            return false;
        }
        return true;
    }
}
