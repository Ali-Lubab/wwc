package com.workshop.wwc.rate;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class RateController {

    private final RateRepository rateRepository;

    @PostMapping("/rates")
    public Rate create(@RequestBody Rate rate) {
        return rateRepository.save(rate);
    }

    @GetMapping("/rates/{id}")
    public Rate getById(@PathVariable Long id) {
        Rate rate = rateRepository.findById(id);
        if (rate == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Rate not found");
        }
        return rate;
    }

    @GetMapping("/rates")
    public List<Rate> getAll() {
        return rateRepository.findAll();
    }

    @GetMapping("/rates/convert")
    public RateDto convert(
            @RequestParam String source,
            @RequestParam String target,
            @RequestParam BigDecimal amount) {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED,
                "Currency conversion is not yet implemented");
    }

    @GetMapping("/rates/currencies")
    public List<String> getAvailableCurrencies() {
        return List.of();
    }

    @GetMapping("/rates/targetCurrencies/{ccy}")
    public List<String> getAvailableTargetCurrencies(@PathVariable String ccy) {
        return List.of();
    }
}
