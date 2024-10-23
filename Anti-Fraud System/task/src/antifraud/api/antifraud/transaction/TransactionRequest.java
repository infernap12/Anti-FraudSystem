package antifraud.api.antifraud.transaction;


import antifraud.validation.CardNumber;
import antifraud.validation.IpAddress;
import jakarta.validation.constraints.Min;

import java.time.LocalDateTime;

public record TransactionRequest(

        @Min(1)
        long amount,
        @IpAddress
        String ip,
        @CardNumber
        String number,
        Region region,
        LocalDateTime date) {
}
