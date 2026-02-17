CREATE TABLE IF NOT EXISTS balance (
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    currency   VARCHAR(3)     NOT NULL UNIQUE,
    amount     DECIMAL(19,4)  NOT NULL
);

CREATE TABLE IF NOT EXISTS recipient (
    id             BIGINT AUTO_INCREMENT PRIMARY KEY,
    first_name     VARCHAR(255) NOT NULL,
    last_name      VARCHAR(255) NOT NULL,
    account_number VARCHAR(255) NOT NULL,
    is_active      BOOLEAN      NOT NULL DEFAULT TRUE
);

CREATE TABLE IF NOT EXISTS rate (
    id              BIGINT AUTO_INCREMENT PRIMARY KEY,
    source_currency VARCHAR(3)      NOT NULL,
    target_currency VARCHAR(3)      NOT NULL,
    rate            DECIMAL(19,8)   NOT NULL,
    UNIQUE (source_currency, target_currency)
);

CREATE TABLE IF NOT EXISTS transfer (
    id              BIGINT AUTO_INCREMENT PRIMARY KEY,
    recipient_id    BIGINT         NOT NULL,
    source_amount   DECIMAL(19,4)  NOT NULL,
    source_currency VARCHAR(3)     NOT NULL,
    target_amount   DECIMAL(19,4)  NOT NULL,
    target_currency VARCHAR(3)     NOT NULL,
    created_at      TIMESTAMP      NOT NULL,
    updated_at      TIMESTAMP      NOT NULL,
    FOREIGN KEY (recipient_id) REFERENCES recipient(id)
);
