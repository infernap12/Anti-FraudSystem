package antifraud.transaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


import java.util.Objects;

@Controller
@RequestMapping("/api/antifraud/transaction")
public final class TransactionController {

    private final TransactionService service;

    public TransactionController(@Autowired TransactionService service) {
        this.service = service;
    }

    // todo handle null incoming amount
    @PostMapping
    ResponseEntity<TransactionResponse> postTransaction(@RequestBody TransactionRequest request) {
        if (request.amount() <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        final TransactionVerdict verdict = service.validate(request.amount());
        final TransactionResponse response = TransactionResponse.fromVerdict(verdict);

        return ResponseEntity.ok(response);

    }

    @GetMapping
    ResponseEntity<TransactionResponse> getTransaction(@RequestBody TransactionRequest request) {
        return ResponseEntity.ok(new TransactionResponse("Nah champ"));
    }
}
