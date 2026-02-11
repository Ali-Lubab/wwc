package com.workshop.wwc.transfer;

import com.workshop.wwc.rate.RateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/transfers")
@RequiredArgsConstructor
public class TransferController {

    private static final Long DEFAULT_CUSTOMER_ID = 1L;

    private final TransferRepository transferRepository;
    private final RateRepository rateRepository;

    @PostMapping
    public Transfer create(@RequestBody Transfer transfer) {
        transfer.setSenderId(DEFAULT_CUSTOMER_ID);
        var exchangeRate =
                rateRepository.findBySourceCurrencyAndTargetCurrency(transfer.getSourceCurrency(), transfer.getTargetCurrency());
        var targetAmount = transfer.getSourceAmount().multiply(exchangeRate.get().getRate());
        transfer.setTargetAmount(targetAmount);
        return transferRepository.save(transfer);
    }

    @GetMapping("/{id}")
    public Transfer getById(@PathVariable Long id) {
        return transferRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Transfer not found"));
    }

    @GetMapping
    public List<Transfer> getAll() {
        return transferRepository.findAll();
    }
}
