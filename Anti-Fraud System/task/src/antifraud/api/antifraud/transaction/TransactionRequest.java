package antifraud.api.antifraud.transaction;


import antifraud.IpAddress;
import jakarta.validation.constraints.Min;
import org.hibernate.validator.constraints.CreditCardNumber;
import org.springframework.validation.Validator;

import java.net.InetAddress;
import java.time.LocalDateTime;

public record TransactionRequest(

        @Min(1)
        long amount,
        @IpAddress
        String ip,
        @CreditCardNumber
        String number,
        Region region,
        LocalDateTime date) {
}
