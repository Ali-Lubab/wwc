package com.workshop.wwc.rate;

import java.math.BigDecimal;

public record RateDto(
        String source,
        String target,
        BigDecimal amount,
        BigDecimal rate,
        BigDecimal result
) {
}
