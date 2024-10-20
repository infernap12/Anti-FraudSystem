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
public class StolenCardService {
    final StolenCardRepository repo;

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

    public StolenCardService(@Autowired StolenCardRepository repo) {
        this.repo = repo;
    }

    public StolenCard getCard(String number) {
        return getCard(AccountNumbers.completeAccountNumber(number));
    }

    public StolenCard getCard(AccountNumber accountNumber) {
        if (!accountNumber.isPrimaryAccountNumberValid()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bad card number");
        }
        return repo
                .findByNumber(accountNumber.getAccountNumber())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Card not found"));
    }


    public StolenCard registerCard(String accountNumber) {
        return registerCard(AccountNumbers.completeAccountNumber(accountNumber));
    }

    public StolenCard registerCard(AccountNumber accountNumber) {
        if (!accountNumber.isPrimaryAccountNumberValid()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bad card number");
        }
        if (repo.existsByNumber(accountNumber.getAccountNumber())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Card already registered");
        }
        val cardE = new StolenCard(accountNumber);

        return repo.save(cardE);
    }
    // todo eventually see i can abstract into some base classes
    public void deleteCard(String accountNumber) {
        deleteCard(getCard(accountNumber));
    }

    public void deleteCard(StolenCard stolenCard) {
        repo.delete(stolenCard);
    }

    public List<StolenCard> getAllCards() {
        return getAllCards(Sort.by(Sort.Order.asc("id")));
    }

    public List<StolenCard> getAllCards(Sort sort) {
        return repo.findAll(sort);
    }
}
