package antifraud.api.antifraud.transaction;

import jakarta.persistence.*;
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
    private long id;

    private long amount;

    private String ip;

    private String number;

    private Region region;

    private LocalDateTime date;

    public InetAddress getInet() throws UnknownHostException {
        return InetAddress.getByName(ip);
    }

    public AccountNumber getAccountNumber() {
        return AccountNumbers.completeAccountNumber(number);
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
