package antifraud.api.antifraud.stolencard;

import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import us.fatehi.creditcardnumber.AccountNumber;
import us.fatehi.creditcardnumber.AccountNumbers;

import java.util.List;

@Service
public class CardService {
    final CardEntityRepository repo;

    public boolean exists(String card) {
        return exists(AccountNumbers.completeAccountNumber(card));
    }

    public boolean exists(AccountNumber accountNumber) {
        return repo.existsByNumber(accountNumber.getAccountNumber());
    }

    public boolean isValid(String card) {
        return isValid(AccountNumbers.completeAccountNumber(card));
    }

    public boolean isValid(AccountNumber accountNumber) {
        return accountNumber.isPrimaryAccountNumberValid();
    }

    public CardService(@Autowired CardEntityRepository repo) {
        this.repo = repo;
    }

    public CardEntity getCard(String number) {
        return getCard(AccountNumbers.completeAccountNumber(number));
    }

    public CardEntity getCard(AccountNumber accountNumber) {
        if (!accountNumber.isPrimaryAccountNumberValid()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bad card number");
        }
        return repo
                .findByNumber(accountNumber.getAccountNumber())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Card not found"));
    }


    public CardEntity registerCard(String accountNumber) {
        return registerCard(AccountNumbers.completeAccountNumber(accountNumber));
    }

    public CardEntity registerCard(AccountNumber accountNumber) {
        if (!accountNumber.isPrimaryAccountNumberValid()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bad card number");
        }
        if (repo.existsByNumber(accountNumber.getAccountNumber())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Card already registered");
        }
        val cardE = new CardEntity(accountNumber);

        return repo.save(cardE);
    }
    // todo eventually see i can abstract into some base classes
    public void deleteCard(String accountNumber) {
        deleteCard(getCard(accountNumber));
    }

    public void deleteCard(CardEntity cardEntity) {
        repo.delete(cardEntity);
    }

    public List<CardEntity> getAllCards() {
        return getAllCards(Sort.by(Sort.Order.asc("id")));
    }

    public List<CardEntity> getAllCards(Sort sort) {
        return repo.findAll(sort);
    }
}
