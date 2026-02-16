-- Row 1: EUR
INSERT INTO balance (currency, amount)
SELECT 'EUR', 1200.00
    WHERE NOT EXISTS (SELECT 1 FROM balance WHERE currency = 'EUR');

-- Row 2: USD
INSERT INTO balance (currency, amount)
SELECT 'USD', 500.00
    WHERE NOT EXISTS (SELECT 1 FROM balance WHERE currency = 'USD');

-- Row 3: GBP
INSERT INTO balance (currency, amount)
SELECT 'GBP', 300.00
    WHERE NOT EXISTS (SELECT 1 FROM balance WHERE currency = 'GBP');

-- Row 4: HUF
INSERT INTO balance (currency, amount)
SELECT 'HUF', 150000.00
    WHERE NOT EXISTS (SELECT 1 FROM balance WHERE currency = 'HUF');


INSERT INTO rate (source_currency, target_currency, rate)
SELECT 'EUR', 'USD', 1.08540000
    WHERE NOT EXISTS (SELECT 1 FROM rate WHERE source_currency = 'EUR' AND target_currency = 'USD');

INSERT INTO rate (source_currency, target_currency, rate)
SELECT 'USD', 'EUR', 0.92100000
    WHERE NOT EXISTS (SELECT 1 FROM rate WHERE source_currency = 'USD' AND target_currency = 'EUR');

INSERT INTO rate (source_currency, target_currency, rate)
SELECT 'EUR', 'GBP', 0.86000000
    WHERE NOT EXISTS (SELECT 1 FROM rate WHERE source_currency = 'EUR' AND target_currency = 'GBP');

INSERT INTO rate (source_currency, target_currency, rate)
SELECT 'GBP', 'EUR', 1.16200000
    WHERE NOT EXISTS (SELECT 1 FROM rate WHERE source_currency = 'GBP' AND target_currency = 'EUR');

INSERT INTO rate (source_currency, target_currency, rate)
SELECT 'USD', 'GBP', 0.79000000
    WHERE NOT EXISTS (SELECT 1 FROM rate WHERE source_currency = 'USD' AND target_currency = 'GBP');

INSERT INTO rate (source_currency, target_currency, rate)
SELECT 'GBP', 'USD', 1.26500000
    WHERE NOT EXISTS (SELECT 1 FROM rate WHERE source_currency = 'GBP' AND target_currency = 'USD');

INSERT INTO rate (source_currency, target_currency, rate)
SELECT 'EUR', 'HUF', 390.50000000
    WHERE NOT EXISTS (SELECT 1 FROM rate WHERE source_currency = 'EUR' AND target_currency = 'HUF');

INSERT INTO rate (source_currency, target_currency, rate)
SELECT 'HUF', 'EUR', 0.00256000
    WHERE NOT EXISTS (SELECT 1 FROM rate WHERE source_currency = 'HUF' AND target_currency = 'EUR');

INSERT INTO rate (source_currency, target_currency, rate)
SELECT 'USD', 'HUF', 360.10000000
    WHERE NOT EXISTS (SELECT 1 FROM rate WHERE source_currency = 'USD' AND target_currency = 'HUF');

INSERT INTO rate (source_currency, target_currency, rate)
SELECT 'HUF', 'USD', 0.00277500
    WHERE NOT EXISTS (SELECT 1 FROM rate WHERE source_currency = 'HUF' AND target_currency = 'USD');

INSERT INTO recipient (first_name, last_name, account_number, is_active)
SELECT 'John', 'Doe', 'DE111', TRUE
    WHERE NOT EXISTS (SELECT 1 FROM recipient WHERE first_name = 'John' AND last_name = 'Doe' AND account_number = 'DE111');

INSERT INTO recipient (first_name, last_name, account_number, is_active)
SELECT 'Jane', 'Doe', 'US222', TRUE
    WHERE NOT EXISTS (SELECT 1 FROM recipient WHERE first_name = 'Jane' AND last_name = 'Doe' AND account_number = 'US222');

INSERT INTO recipient (first_name, last_name, account_number, is_active)
SELECT 'Michael', 'Scott', 'US333', TRUE
    WHERE NOT EXISTS (SELECT 1 FROM recipient WHERE first_name = 'Michael' AND last_name = 'Scott' AND account_number = 'US333');

INSERT INTO recipient (first_name, last_name, account_number, is_active)
SELECT 'Pam', 'Beesly', 'DE444', TRUE
    WHERE NOT EXISTS (SELECT 1 FROM recipient WHERE first_name = 'Pam' AND last_name = 'Beesly' AND account_number = 'DE444');

INSERT INTO recipient (first_name, last_name, account_number, is_active)
SELECT 'Jim', 'Halpert', 'GB555', TRUE
    WHERE NOT EXISTS (SELECT 1 FROM recipient WHERE first_name = 'Jim' AND last_name = 'Halpert' AND account_number = 'GB555');

INSERT INTO transfer (recipient_id, source_amount, source_currency, target_amount, target_currency, created_at, updated_at)
SELECT (SELECT id FROM recipient WHERE account_number = 'DE111'), 100.00, 'EUR', 108.54, 'USD', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
    WHERE NOT EXISTS (SELECT 1 FROM transfer WHERE recipient_id = (SELECT id FROM recipient WHERE account_number = 'DE111') AND source_currency = 'EUR' AND target_currency = 'USD');

INSERT INTO transfer (recipient_id, source_amount, source_currency, target_amount, target_currency, created_at, updated_at)
SELECT (SELECT id FROM recipient WHERE account_number = 'US222'), 50.00, 'USD', 46.05, 'EUR', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
    WHERE NOT EXISTS (SELECT 1 FROM transfer WHERE recipient_id = (SELECT id FROM recipient WHERE account_number = 'US222') AND source_currency = 'USD' AND target_currency = 'EUR');

INSERT INTO transfer (recipient_id, source_amount, source_currency, target_amount, target_currency, created_at, updated_at)
SELECT (SELECT id FROM recipient WHERE account_number = 'US333'), 200.00, 'EUR', 217.08, 'USD', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
    WHERE NOT EXISTS (SELECT 1 FROM transfer WHERE recipient_id = (SELECT id FROM recipient WHERE account_number = 'US333') AND source_currency = 'EUR' AND target_currency = 'USD');

