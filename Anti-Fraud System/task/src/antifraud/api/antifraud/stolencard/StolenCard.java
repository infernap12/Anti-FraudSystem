package antifraud.api.antifraud.stolencard;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import us.fatehi.creditcardnumber.AccountNumber;
import us.fatehi.creditcardnumber.AccountNumbers;

@Getter
@Setter
@Entity
@RequiredArgsConstructor
@Table(name = "STOLEN_CARDS")
public class StolenCard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "card_number", unique = true)
    private String number;

    public StolenCard(AccountNumber accountNumber) {
        this.number = accountNumber.getAccountNumber();
    }

    public AccountNumber asAccountNumber() {
        return AccountNumbers.completeAccountNumber(number);
    }
}