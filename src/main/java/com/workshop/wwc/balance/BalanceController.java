package com.workshop.wwc.balance;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BalanceController {

    private final BalanceRepository balanceRepository;

    @PostMapping("/balances")
    public Balance create(@RequestBody Balance balance) {
        return balanceRepository.save(balance);
    }

    @GetMapping("/balances/{id}")
    public Balance getById(@PathVariable Long id) {
        return balanceRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Balance not found"));
    }

    @GetMapping("/balances")
    public List<Balance> getAll() {
        return balanceRepository.findAll();
    }
}
