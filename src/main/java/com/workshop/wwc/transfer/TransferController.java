package com.workshop.wwc.transfer;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/transfers")
@RequiredArgsConstructor
public class TransferController {

    private final TransferRepository transferRepository;

    @PostMapping
    public Transfer create(@RequestBody Transfer transfer) {
        return transferRepository.save(transfer);
    }

    @GetMapping("/{id}")
    public Transfer getById(@PathVariable Long id) {
        return transferRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Transfer not found"));
    }

    @GetMapping
    public List<Transfer> getAll() {
        return transferRepository.findAll();
    }
}

