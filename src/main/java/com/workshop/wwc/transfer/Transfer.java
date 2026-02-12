package com.workshop.wwc.transfer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Transfer {

    private Long id;

    private Long recipientId;

    private BigDecimal sourceAmount;

    private String sourceCurrency;

    private BigDecimal targetAmount;

    private String targetCurrency;

    private Instant createdAt;

    private Instant updatedAt;
}
