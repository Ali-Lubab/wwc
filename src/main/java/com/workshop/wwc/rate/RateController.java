package com.workshop.wwc.rate;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/rates")
@RequiredArgsConstructor
public class RateController {

    private final RateRepository rateRepository;

    @PostMapping
    public Rate create(@RequestBody Rate rate) {
        return rateRepository.save(rate);
    }

    @GetMapping("/{id}")
    public Rate getById(@PathVariable Long id) {
        return rateRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Rate not found"));
    }

    @GetMapping
    public List<Rate> getAll() {
        return rateRepository.findAll();
    }

    @GetMapping("/convert")
    public RateDto convert(
            @RequestParam String source,
            @RequestParam String target,
            @RequestParam BigDecimal amount) {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED,
                "Currency conversion is not yet implemented");
    }

    @GetMapping("/currencies")
    public List<String> getAvailableCurrencies() {
        return List.of();
    }

    @GetMapping("/targetCurrencies/{ccy}")
    public List<String> getAvailableTargetCurrencies(@PathVariable String ccy) {
        return List.of();
    }
}
