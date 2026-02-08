package com.workshop.wwc.rate;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface RateRepository extends JpaRepository<Rate, Long> {
    Optional<Rate> findBySourceCurrencyAndTargetCurrency(
            String sourceCurrency,
            String targetCurrency
    );
}
