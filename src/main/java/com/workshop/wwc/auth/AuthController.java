package com.workshop.wwc.auth;

import com.workshop.wwc.customer.Customer;
import com.workshop.wwc.customer.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final CustomerRepository customerRepository;

    @PostMapping("/login")
    public Customer login(@RequestBody LoginRequest request) {
        return customerRepository
                .findByEmailAddressAndPassword(
                        request.getEmail(),
                        request.getPassword()
                )
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.UNAUTHORIZED,
                                "Invalid credentials"
                        )
                );
    }
}

