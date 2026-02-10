package com.workshop.wwc.transfer;

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
public class Transfer extends Auditable {

    private Long id;

    private Long senderId;

    private Long recipientId;

    private BigDecimal sourceAmount;

    private String sourceCurrency;

    private BigDecimal targetAmount;

    private String targetCurrency;
}
