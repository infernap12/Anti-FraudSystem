package antifraud.api.antifraud;

import antifraud.IpAddress;
import antifraud.IpAddressValidator;
import antifraud.api.antifraud.stolencard.StolenCard;
import antifraud.api.antifraud.stolencard.StolenCardCreationRequest;
import antifraud.api.antifraud.stolencard.StolenCardView;
import antifraud.api.antifraud.stolencard.StolenCardService;
import antifraud.api.antifraud.suspiciousIp.SuspiciousIpView;
import antifraud.api.antifraud.suspiciousIp.SuspiciousIpService;
import antifraud.api.antifraud.suspiciousIp.SuspiciousIpCreationRequest;
import antifraud.api.antifraud.transaction.*;
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
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/api/antifraud")
public class AntiFraudController {
    private final SuspiciousIpService ipService;
    private final StolenCardService cardService;
    private final TransactionService service;

    public AntiFraudController(@Autowired TransactionService service,
                               @Autowired SuspiciousIpService ipService,
                               @Autowired StolenCardService cardService) {
        this.service = service;
        this.ipService = ipService;
        this.cardService = cardService;
    }

    @PostMapping({"/stolencard", "/stolencard/"})
    public ResponseEntity<StolenCardView> postCard(@RequestBody StolenCardCreationRequest card) {
        final StolenCard stolenCard = cardService.registerCard(card.number());
        return ResponseEntity.ok(new StolenCardView(stolenCard));
    }

    @Transactional
    @DeleteMapping({"/stolencard/{number}", "/stolencard/{number}/"})
    public ResponseEntity<Map<String, String>> deleteCard(@PathVariable String number) {
        val body = Map.of("status", "Card " + number + " successfully removed!");
        cardService.deleteCard(number);
        return ResponseEntity.ok(body);
    }

    @GetMapping({"/stolencard", "/stolencard/"})
    public ResponseEntity<List<StolenCardView>> getCard() {
        val cards = cardService.getAllCards();
        val body = cards.stream().map(StolenCardView::new).toList();
        return ResponseEntity.ok(body);
    }

    @PostMapping({"/suspicious-ip", "/suspicious-ip/"})
    public ResponseEntity<SuspiciousIpView> postIp(
            @RequestBody
            @Validated
            SuspiciousIpCreationRequest request
    ) {
        val ipEntity = ipService.createIP(request.ip());
        return ResponseEntity.ok(new SuspiciousIpView(ipEntity));
    }

    @Transactional
    @DeleteMapping({"/suspicious-ip/{ip}", "/suspicious-ip/{ip}/"})
    ResponseEntity<Map<String, String>> deleteIp(
            @PathVariable
            String ip
    ) {
        if (!new IpAddressValidator().isValid(ip)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        ipService.deleteIp(ip);
        val response = Map.of("status", "IP " + ip + " successfully removed!");
        return ResponseEntity.ok(response);
    }

    @GetMapping({"/suspicious-ip", "/suspicious-ip/"})
    ResponseEntity<List<SuspiciousIpView>> getIps() {
        val ips = ipService.getAllIps();
        val body = ips.stream().map(SuspiciousIpView::new).toList();
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
        final Transaction transaction = service.saveTransaction(Transaction.fromRequest(request));
        final EnumMap<TransactionTests, TransactionVerdict> resultSet = service.validate(transaction);


        final TransactionResponse response = TransactionResponse.fromTestMap(resultSet);

        return ResponseEntity.ok(response);
    }


}
