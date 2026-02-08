package com.workshop.wwc.transfer;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TransferRepository extends JpaRepository<Transfer, Long> {
    List<Transfer> findBySenderIdOrderByCreatedAtDesc(Long senderId);
}
