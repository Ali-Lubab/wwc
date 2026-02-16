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
    public Map<String, Object> convert(
            @RequestParam String source,
            @RequestParam String target,
            @RequestParam BigDecimal amount) {
        Optional<Rate> rateOpt = rateRepository.findBySourceCurrencyAndTargetCurrency(source, target);
        if (rateOpt.isEmpty()) {
            return Map.of("error", "Exchange rate not found for " + source + " to " + target);
        }
        BigDecimal result = amount.multiply(rateOpt.get().getRate()).setScale(2, RoundingMode.HALF_UP);
        return Map.of(
                "source", source,
                "target", target,
                "amount", amount,
                "rate", rateOpt.get().getRate(),
                "result", result
        );
    }

    @GetMapping("/currencies")
    public List<String> getAvailableCurrencies() {
        return rateRepository.getAllAvailableCurrencies();
    }

    @GetMapping("/targetCurrencies/{ccy}")
    public List<String> getAvailableTargetCurrencies(@PathVariable String ccy) {
        if (!rateRepository.getAllAvailableCurrencies().contains(ccy)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid source currency");
        }
        return rateRepository.getAllTargetCurrenciesFor(ccy);
    }
}
