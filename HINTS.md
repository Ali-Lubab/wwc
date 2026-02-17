## Tasks
If you get stuck with the tasks, use this file to get some help on how to implement the features.

This is a money transfer application. The aim is to be able to create a recipient, check currency exchange rates and create a transfer.

### 1. Implement Exchange Rate Calculator

**What you need to do:**
- Edit the `GET /rates/convert` endpoint in `RateController`
- Look up the exchange rate from `RateRepository` using `findBySourceCurrencyAndTargetCurrency()`
- Calculate the converted amount by multiplying the input amount by the rate
- Return the result (original amount, converted amount, rate used, and currencies)
- Handle the case where a currency pair doesn't exist (return an appropriate error message)

**Files to modify:** `RateController.java`

### 2. Add Email Field to Recipient

**What you need to do:**
- Add a new `email` field (type `String`) to the `Recipient` entity class
- Update `schema.sql` to add an `email` column to the recipient table
- Update `RecipientRepository` to include the email field in the row mapper, INSERT, and UPDATE statements

**Files to modify:** `Recipient.java`, `RecipientRepository.java`, `schema.sql`

### 3. Implement Transfer Creation with Balance Updates

**What you need to do:**
- **3.a.** Verify the basic transfer creation works - create a transfer and confirm it appears in the transfers list on the home page
- **3.b.** Deduct the source amount from the appropriate balance when a transfer is created:
    - Look up the balance for the source currency using `BalanceRepository.findByCurrency()`
    - Subtract the transfer's source amount from the balance
    - Save the updated balance
- **3.c.** Add validation to prevent transfers when the balance is insufficient:
    - Before creating the transfer, check if the balance has enough funds
    - If not, return an error response (e.g., HTTP 400 Bad Request with a message)

**Files to modify:** `TransferController.java`

### 4. Refactor Transfer Creation Logic into a Service

**What you need to do:**
- Create a new class `TransferService.java` in the `transfer` package
- Add the `@Service` annotation to make it a Spring-managed bean
- Move the transfer creation logic from `TransferConroller` into a method like `create(Transfer transfer)`
- Split that method into smaller parts
- Inject `RateRepository`, `BalanceRepository` and `TransferRepository` into the service
- Update `TransferController` to inject and use `TransferController` instead of containing the logic directly

**Files to create:** `TransferService.java`

**Files to modify:**  `TransferController.java`

### 5. Implement Currency Drop-downs for Exchange Rate Calculator

**What you need to do:**

**5.a. Create endpoint for available source currencies**
- Add a new endpoint `GET /rates/sources` in `RateController`
- Query all rates from `RateRepository.findAll()`
- Extract and return a list of unique source currencies (e.g., `["EUR", "USD", "GBP", "HUF"]`)

**5.b. Create endpoint for available target currencies**
- Add a new endpoint `GET /rates/targets?source={currency}` in `RateController`
- Query rates that match the given source currency
- Return a list of available target currencies for that source (e.g., for EUR: `["USD", "GBP", "HUF"]`)

**5.c. Update the HTML to use drop-downs**
- In `exchange-rate-calculator.html`, replace the text inputs with `<select>` elements
- Modify the JavaScript file `js/calculator.js` to:
    - On page load, fetch `/rates/sources` and populate the source currency drop-down
    - When source currency changes, fetch `/rates/targets?source=XXX` and populate the target currency drop-down
- Make sure the form submission still works with the new select elements

**Files to modify:** `RateController.java`, `exchange-rate-calculator.html`
