package antifraud.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

/**
 * Annotation to mark a field that must be a valid IP address.
 * <p>
 * This annotation can be applied to String fields within a class
 * to ensure that they conform to the format of a valid IP address.
 * The underlying validation is implemented by the {@link IpAddressValidator} class.
 * </p>
 * <p>
 * Example usage might include fields in a request object where IP addresses
 * need to be validated before being processed.
 * </p>
 *
 * <pre>
 * &#064;IpAddress
 * private String ipAddress;
 * </pre>
 *
 * Validation messages can be customized using the "message" attribute.
 * The default message key is "{IpAddress.invalid}".
 */
@Target({FIELD})
@Retention(RUNTIME)
@Constraint(validatedBy = IpAddressValidator.class)
@Documented
public @interface IpAddress {
    String message() default "{IpAddress.invalid}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
