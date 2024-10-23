package antifraud.limit;

import antifraud.api.antifraud.transaction.TransactionVerdict;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity()
@Table(name = "limits")
public class Limit {
    @Id
    @GeneratedValue
    private long id;

    @Enumerated(EnumType.STRING)
    @Column(unique = true)
    private TransactionVerdict name;
    @Column(name = "limit_value")
    private int value;

    public Limit(TransactionVerdict name, int limit) {
        this.name = name;
        this.value = limit;
    }

    @PostLoad
    public void postLoad() {

    }
}