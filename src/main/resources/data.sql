INSERT INTO customer
(id, first_name, last_name, dob, address, email_address, password)
VALUES
(1, 'Default', 'User', '2000-01-01 00:00:00', 'Default Address', 'default@wwc.com', 'default');

INSERT INTO balance
(id, currency, amount, owner_id)
VALUES
(1, 'EUR', 1200.00, 1),
(2, 'USD', 500.00, 1),
(3, 'GBP', 300.00, 1),
(4, 'HUF', 150000.00, 1);

INSERT INTO recipient
(id, first_name, last_name, currency, account_number, is_active, owner_id)
VALUES
(1, 'John', 'Doe', 'EUR', 'DE111', TRUE, 1),
(2, 'Jane', 'Doe', 'USD', 'US222', TRUE, 1),
(3, 'Michael', 'Scott', 'USD', 'US333', TRUE, 1),
(4, 'Pam', 'Beesly', 'EUR', 'DE444', TRUE, 1),
(5, 'Jim', 'Halpert', 'GBP', 'GB555', TRUE, 1);

INSERT INTO rate
(id, source_currency, target_currency, rate)
VALUES
(1, 'EUR', 'USD', 1.08540000),
(2, 'USD', 'EUR', 0.92100000),
(3, 'EUR', 'GBP', 0.86000000),
(4, 'GBP', 'EUR', 1.16200000),
(5, 'USD', 'GBP', 0.79000000),
(6, 'GBP', 'USD', 1.26500000),
(7, 'EUR', 'HUF', 390.50000000),
(8, 'HUF', 'EUR', 0.00256000),
(9, 'USD', 'HUF', 360.10000000),
(10, 'HUF', 'USD', 0.00277500);

INSERT INTO transfer
(id, sender_id, recipient_id, source_amount, source_currency, target_amount, target_currency, created_at, updated_at)
VALUES
(1, 1, 1, 100.00, 'EUR', 108.54, 'USD', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 1, 2, 50.00, 'USD', 46.05, 'EUR', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 1, 3, 200.00, 'EUR', 217.08, 'USD', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

ALTER TABLE customer ALTER COLUMN id RESTART WITH 2;
ALTER TABLE balance ALTER COLUMN id RESTART WITH 5;
ALTER TABLE recipient ALTER COLUMN id RESTART WITH 6;
ALTER TABLE rate ALTER COLUMN id RESTART WITH 11;
ALTER TABLE transfer ALTER COLUMN id RESTART WITH 4;
