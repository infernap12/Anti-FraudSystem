package antifraud.api.antifraud;

import antifraud.api.antifraud.stolencard.CardEntity;
import antifraud.api.antifraud.stolencard.CardRequest;
import antifraud.api.antifraud.stolencard.CardResponse;
import antifraud.api.antifraud.stolencard.CardService;
import antifraud.api.antifraud.suspiciousIp.IpEntityView;
import antifraud.api.antifraud.suspiciousIp.IpService;
import antifraud.api.antifraud.suspiciousIp.IpRequest;
import antifraud.api.antifraud.transaction.TransactionRequest;
import antifraud.api.antifraud.transaction.TransactionResponse;
import antifraud.api.antifraud.transaction.TransactionService;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.stereotype.Controller;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/api/antifraud")
public class AntiFraudController {
    private final IpService ipService;
    private final CardService cardService;
    private final TransactionService service;

    public AntiFraudController(@Autowired TransactionService service,
                               @Autowired IpService ipService,
                               @Autowired CardService cardService) {
        this.service = service;
        this.ipService = ipService;
        this.cardService = cardService;
    }

    @PostMapping({"/stolencard", "/stolencard/"})
    public ResponseEntity<CardResponse> postCard(@RequestBody CardRequest card) {
        final CardEntity cardEntity = cardService.registerCard(card.number());
        return ResponseEntity.ok(new CardResponse(cardEntity));
    }

    @Transactional
    @DeleteMapping({"/stolencard/{number}", "/stolencard/{number}/"})
    public ResponseEntity<Map<String, String>> deleteCard(@PathVariable String number) {
        val body = Map.of("status", "Card " + number + " successfully removed!");
        cardService.deleteCard(number);
        return ResponseEntity.ok(body);
    }

    @GetMapping({"/stolencard", "/stolencard/"})
    public ResponseEntity<List<CardResponse>> getCard() {
        val cards = cardService.getAllCards();
        val body = cards.stream().map(CardResponse::new).toList();
        return ResponseEntity.ok(body);
    }

    @PostMapping({"/suspicious-ip", "/suspicious-ip/"})
    public ResponseEntity<IpEntityView> postIp(@RequestBody IpRequest request) {
        final InetAddress inetAddress;
        try {
            inetAddress = InetAddress.getByName(request.ip());
        } catch (UnknownHostException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid ip");
        }
        val ipEntity = ipService.createIP(inetAddress);
        return ResponseEntity.ok(new IpEntityView(ipEntity));
    }

    @Transactional
    @DeleteMapping({"/suspicious-ip/{ip}", "/suspicious-ip/{ip}/"})
    ResponseEntity<Map<String, String>> deleteIp(@PathVariable String ip) {
        ipService.deleteIp(ip);
        val response = Map.of("status", "IP " + ip + " successfully removed!");
        return ResponseEntity.ok(response);
    }

    @GetMapping({"/suspicious-ip", "/suspicious-ip/"})
    ResponseEntity<List<IpEntityView>> getIps() {
        val ips = ipService.getAllIps();
        val body = ips.stream().map(IpEntityView::new).toList();
        return ResponseEntity.ok(body);
    }

    // reason list
    // if reason list empty normal path
    //else bad return
    //todo refactor please, its god awful
    //consider mapping using bool table
    @PostMapping({"/transaction", "/transaction/"})
    ResponseEntity<TransactionResponse> postTransaction(@RequestBody @Validated TransactionRequest request) {
        if (!cardService.isValid(request.number()) || !ipService.isValid(request.ip())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        val isCard = cardService.exists(request.number());
        val isIp = ipService.exists(request.ip());
        val amountVerdict = service.validate(request.amount());




        final TransactionResponse response = TransactionResponse.fromVerdict(amountVerdict, isCard, isIp);

        return ResponseEntity.ok(response);
    }


}
