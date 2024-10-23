package antifraud.api.antifraud.transaction;

import antifraud.validation.CardNumber;
import antifraud.validation.IpAddress;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.StringSerializer;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import us.fatehi.creditcardnumber.AccountNumber;
import us.fatehi.creditcardnumber.AccountNumbers;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity(name = "Transaction")
@Table(name = "transactions")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("transactionId")
    private long id;

    @Min(1)
    @Column(nullable = false)
    private long amount;

    @IpAddress
    private String ip;

    @CardNumber
    @NotBlank
    private String number;

    @NotNull
    @Enumerated
    private Region region;

    @NotNull
    private LocalDateTime date;


    @Enumerated
    private TransactionVerdict result;

    @Enumerated
    @JsonIgnore
    private TransactionVerdict feedback;

    @JsonIgnore
    public InetAddress getInet() throws UnknownHostException {
        return InetAddress.getByName(ip);
    }

    @JsonIgnore
    public AccountNumber getAccountNumber() {
        return AccountNumbers.completeAccountNumber(number);
    }
@JsonGetter("feedback")
    private String getStringFeedback() {
        return feedback == null ? "" : feedback.toString();
    }

    public static Transaction fromRequest(TransactionRequest request) {
        val transaction = new Transaction();
        transaction.setAmount(request.amount());
        transaction.setIp(request.ip());
        transaction.setNumber(request.number());
        transaction.setRegion(request.region());
        transaction.setDate(request.date());
        return transaction;
    }
}
