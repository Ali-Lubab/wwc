package com.workshop.wwc.rate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Rate {

    private Long id;

    private String sourceCurrency;

    private String targetCurrency;

    private BigDecimal rate;
}
