package com.workshop.wwc.recipient;

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
@RequestMapping("/recipients")
@RequiredArgsConstructor
public class RecipientController {

    private final RecipientRepository recipientRepository;

    @PostMapping
    public Recipient create(@RequestBody Recipient recipient) {
        return recipientRepository.save(recipient);
    }

    @GetMapping("/{id}")
    public Recipient getById(@PathVariable Long id) {
        return recipientRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Recipient not found"));
    }

    @GetMapping
    public List<Recipient> getAll() {
        return recipientRepository.findAll();
    }
}

