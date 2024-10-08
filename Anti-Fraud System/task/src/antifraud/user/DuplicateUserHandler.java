package antifraud.user;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ResponseStatus(HttpStatus.CONFLICT)
class UsernameInUseException extends RuntimeException {
    UsernameInUseException() {
        super("User already exists");
    }

    UsernameInUseException(String message) {
        super(message);
    }
}

@ResponseStatus(HttpStatus.NOT_FOUND)
class UsernameNotFoundException extends RuntimeException {
    UsernameNotFoundException() {
        super("User already exists");
    }

    UsernameNotFoundException(String message) {
        super(message);
    }
}