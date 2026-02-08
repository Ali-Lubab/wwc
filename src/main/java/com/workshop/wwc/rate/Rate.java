package com.workshop.wwc.rate;

import com.workshop.wwc.common.Auditable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(
        name = "rate",
        uniqueConstraints = @UniqueConstraint(
                columnNames = {"sourceCurrency", "targetCurrency"}
        )
)
@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Rate extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 3)
    private String sourceCurrency;

    @Column(nullable = false, length = 3)
    private String targetCurrency;

    @Column(nullable = false, precision = 19, scale = 8)
    private BigDecimal rate;
}

