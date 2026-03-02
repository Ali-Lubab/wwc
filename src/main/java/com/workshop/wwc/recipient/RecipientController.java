package com.workshop.wwc.recipient;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class RecipientController {

    private final RecipientRepository recipientRepository;

    @PostMapping("/recipients")
    public Recipient create(@RequestBody Recipient recipient) {
        return recipientRepository.save(recipient);
    }

    @GetMapping("/recipients/{id}")
    public Recipient getById(@PathVariable Long id) {
        Recipient recipient = recipientRepository.findById(id);
        if (recipient == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Recipient not found");
        }
        return recipient;
    }

    @GetMapping("/recipients")
    public List<Recipient> getAll() {
        return recipientRepository.findAll();
    }
}
