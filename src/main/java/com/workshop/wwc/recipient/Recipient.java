package com.workshop.wwc.recipient;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Recipient {

    private Long id;

    private String firstName;

    private String lastName;

    private String currency;

    private String accountNumber;

    @Builder.Default
    private boolean isActive = true;

    private Long ownerId;
}
