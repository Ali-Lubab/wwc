package com.workshop.wwc.customer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Customer {

    private Long id;

    private String firstName;

    private String lastName;

    private Instant dob;

    private String address;

    private String emailAddress;

    private String password;
}
