package com.workshop.wwc.balance;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface BalanceRepository extends JpaRepository<Balance, Long> {
    Optional<Balance> findByOwnerIdAndCurrency(Long ownerId, String currency);
}
