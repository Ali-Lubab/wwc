## Tasks
If you get stuck with the tasks, use this file to get some help on how to implement the features.

This is a money transfer application. The aim is to be able to create a recipient, check currency exchange rates and create a transfer.

## Domains
- **Recipient**: Represents a recipient of money transfers. Contains fields like `id`, `name`, `currency`, and `email`.
- **Rate**: Represents an exchange rate between two currencies. Contains fields like `id`, `sourceCurrency`, `targetCurrency`, and `rate`. We treat these as fix for our use case.
- **Transfer**: Represents a money transfer between a sender and a recipient. Contains fields like `id`, `recipientId`, `sourceCurrency`, `targetCurrency`, `sourceAmount`, `targetAmount`, and `timestamp`.
- **Balance**: Represents the user's balance for a specific currency. Contains fields like `id`, `currency`, and `amount`.

### 1. Implement Exchange Rate Calculator

**Test it:** Open http://localhost:8080/exchange-rate-calculator.html in the browser and try converting 100 EUR to USD.

**What you need to do:**
- Implement `GET /rates/convert` endpoint in `RateController` that accepts source currency, target currency, and amount as parameters
- Look up the exchange rate from `RateRepository` using `findBySourceCurrencyAndTargetCurrency()`
- Calculate the converted amount by multiplying the input amount by the rate
- Return the result (original amount, converted amount, rate used, and currencies)
- Handle the case where a currency pair doesn't exist (throw an appropriate error message)

**Files to modify:** `RateController.java`

### 2. Add Email Field to Recipient

**Test it:** Add a new recipient with an email address and verify it appears in the recipients list.

**What you need to do:**
- Add a new `email` field (type `String`) to the `Recipient` entity class
- Update `schema.sql` to add an `email` column to the recipient table
- Update `RecipientRepository` to include the email field in the row mapper, INSERT, and UPDATE statements

**Files to modify:** `Recipient.java`, `RecipientRepository.java`, `schema.sql`

### 3. Implement Transfer Creation with Balance Updates

**Test it:**
1. Check your EUR balance on the home page
2. Create a transfer from EUR to USD
3. Verify the EUR balance decreased by the source amount
4. Try to create a transfer larger than your remaining balance - it should fail

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

**Test it:** Verify that the transfer creation still work exactly as before.

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

**Test it:**
1. Open the exchange rate calculator page
2. The source currency drop-down should show all available currencies
3. Select a source currency - the target drop-down should update to show only valid pairs
4. Perform a conversion and verify it still works

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
