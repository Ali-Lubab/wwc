CREATE TABLE IF NOT EXISTS customer (
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    first_name  VARCHAR(255) NOT NULL,
    last_name   VARCHAR(255) NOT NULL,
    dob         TIMESTAMP    NOT NULL,
    address     VARCHAR(255),
    email_address VARCHAR(255) NOT NULL UNIQUE,
    password    VARCHAR(255) NOT NULL,
    created_at  TIMESTAMP    NOT NULL,
    updated_at  TIMESTAMP    NOT NULL
);

CREATE TABLE IF NOT EXISTS balance (
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    currency   VARCHAR(3)     NOT NULL,
    amount     DECIMAL(19,4)  NOT NULL,
    owner_id   BIGINT         NOT NULL,
    created_at TIMESTAMP      NOT NULL,
    updated_at TIMESTAMP      NOT NULL,
    UNIQUE (owner_id, currency),
    FOREIGN KEY (owner_id) REFERENCES customer(id)
);

CREATE TABLE IF NOT EXISTS recipient (
    id             BIGINT AUTO_INCREMENT PRIMARY KEY,
    first_name     VARCHAR(255) NOT NULL,
    last_name      VARCHAR(255) NOT NULL,
    currency       VARCHAR(3)   NOT NULL,
    account_number VARCHAR(255) NOT NULL,
    is_active      BOOLEAN      NOT NULL DEFAULT TRUE,
    owner_id       BIGINT       NOT NULL,
    created_at     TIMESTAMP    NOT NULL,
    updated_at     TIMESTAMP    NOT NULL,
    FOREIGN KEY (owner_id) REFERENCES customer(id)
);

CREATE TABLE IF NOT EXISTS rate (
    id              BIGINT AUTO_INCREMENT PRIMARY KEY,
    source_currency VARCHAR(3)      NOT NULL,
    target_currency VARCHAR(3)      NOT NULL,
    rate            DECIMAL(19,8)   NOT NULL,
    created_at      TIMESTAMP       NOT NULL,
    updated_at      TIMESTAMP       NOT NULL,
    UNIQUE (source_currency, target_currency)
);

CREATE TABLE IF NOT EXISTS transfer (
    id              BIGINT AUTO_INCREMENT PRIMARY KEY,
    sender_id       BIGINT         NOT NULL,
    recipient_id    BIGINT         NOT NULL,
    source_amount   DECIMAL(19,4)  NOT NULL,
    source_currency VARCHAR(3)     NOT NULL,
    target_amount   DECIMAL(19,4)  NOT NULL,
    target_currency VARCHAR(3)     NOT NULL,
    created_at      TIMESTAMP      NOT NULL,
    updated_at      TIMESTAMP      NOT NULL,
    FOREIGN KEY (sender_id)    REFERENCES customer(id),
    FOREIGN KEY (recipient_id) REFERENCES recipient(id)
);
