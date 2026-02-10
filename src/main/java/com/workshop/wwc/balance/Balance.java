package com.workshop.wwc.balance;

import com.workshop.wwc.common.Auditable;
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
public class Balance extends Auditable {

    private Long id;

    private String currency;

    private BigDecimal amount;

    private Long ownerId;
}
