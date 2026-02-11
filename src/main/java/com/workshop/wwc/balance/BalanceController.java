package com.workshop.wwc.balance;

import com.workshop.wwc.customer.CustomerRepository;
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
@RequestMapping("/balances")
@RequiredArgsConstructor
public class BalanceController {

    private static final Long DEFAULT_CUSTOMER_ID = 1L;

    private final BalanceRepository balanceRepository;
    private final CustomerRepository customerRepository;

    @PostMapping
    public Balance create(@RequestBody Balance balance) {
        balance.setOwner(customerRepository.getReferenceById(DEFAULT_CUSTOMER_ID));
        return balanceRepository.save(balance);
    }

    @GetMapping("/{id}")
    public Balance getById(@PathVariable Long id) {
        return balanceRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Balance not found"));
    }

    @GetMapping
    public List<Balance> getAll() {
        return balanceRepository.findAll().stream()
                .filter(b -> b.getOwner().getId().equals(DEFAULT_CUSTOMER_ID))
                .toList();
    }
}
