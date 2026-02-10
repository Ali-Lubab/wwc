package com.workshop.wwc.common;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public abstract class Auditable {

    private Instant createdAt;

    private Instant updatedAt;
}
