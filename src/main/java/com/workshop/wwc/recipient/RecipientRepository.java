package com.workshop.wwc.recipient;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface RecipientRepository extends JpaRepository<Recipient, Long> {
    List<Recipient> findByOwnerIdAndIsActiveTrue(Long ownerId);
}
