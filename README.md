# Getting started

## What do you need?
- Java
- Intellij - hopefully you're already in there :)
- Install Lombok plugin in Intellij and enable annotation processing in settings
- You can use AI tools, but don't use Agentic AIs that can write code for you without your input

## Basic commands
- `./gradlew bootRun` starts spring boot application.
- `./gradlew clean` clears the build directory.
- `./grardlew build` builds the project.
- `--info` at the end of gradle commands shows more detailed logs.
- `ctrl` + `c` in the terminal exits running command.

Alternatively, if you want to run the application from Intellij, you can open `WwcApplication.java` and click the green play button next to the `main` method.
![run-application.png](docs/images/run-application.png)

If you change something on the backend code, you need to stop the application and start it again for the changes to take effect.

## UI
- You need to start the backend application first
- Once the application is running, you can access the UI by opening http://localhost:8080 in your web browser.
- On the UI, you can navigate to the different screens

## Useful Intellij shortcuts
- Press `option` and `F12` at the same time to open the terminal. 
- Double press any of the `shift` buttons to look up a file.
- In `.java` files, hold `command` button on your keyboard and click on a variable name to go to the declaration.

## Logging in to H2 DB console
- Open http://localhost:8080/h2-console in your browser
- Use the following credentials to log in:
  - **JDBC URL**: `jdbc:h2:file:./data/wwc_db;AUTO_SERVER=TRUE`
  - **Username**: `admin`
  - **Password**: `admin`
  - Make sure to select the correct JDBC URL and provide the correct username and password to access the H2 database console.
  - Once logged in, you can execute SQL queries to interact with the database and view the data stored in the tables.
- **You can reset the database by deleting the `data` folder in the project directory. This will remove all existing data and allow you to start with a fresh database when you run the application again.**

![h2_console.png](docs/images/h2_console.png)
  
## DB design
![db_design.png](docs/images/db_design.png)

## Domains
- **Recipient**: Represents a recipient of money transfers. Contains fields like `id`, `name`, `currency`, and `email`.
- **Rate**: Represents an exchange rate between two currencies. Contains fields like `id`, `sourceCurrency`, `targetCurrency`, and `rate`. We treat these as fix for our use case.
- **Transfer**: Represents a money transfer between a sender and a recipient. Contains fields like `id`, `recipientId`, `sourceCurrency`, `targetCurrency`, `sourceAmount`, `targetAmount`, and `timestamp`.
- **Balance**: Represents the user's balance for a specific currency. Contains fields like `id`, `currency`, and `amount`.

## Tasks
This is a money transfer application. The aim is to be able to create a recipient, check currency exchange rates and create a transfer. 

### 1. Implement Exchange Rate Calculator
Build a currency conversion feature that allows users to calculate how much money they will receive when exchanging between currencies.

**Test it:** Open http://localhost:8080/exchange-rate-calculator.html in the browser and try converting 100 EUR to USD.

### 2. Add Email Field to Recipient
Extend the Recipient entity to store email addresses, allowing users to associate contact information with their recipients.

**Test it:** Add a new recipient with an email address and verify it appears in the recipients list.

### 3. Implement Transfer Creation with Balance Updates
Complete the money transfer functionality so that when a transfer is created, the sender's balance is deducted appropriately.

**Test it:**
1. Check your EUR balance on the home page
2. Create a transfer from EUR to USD
3. Verify the EUR balance decreased by the source amount
4. Try to create a transfer larger than your remaining balance - it should fail

### 4. Refactor Transfer Creation Logic into a Service
Extract the transfer creation business logic from controllers into a dedicated service class. This follows the single responsibility principle and makes the code more maintainable and testable.

**Why this matters:** Controllers should only handle HTTP requests/responses. Business logic (like currency conversion calculations) belongs in service classes. This separation makes code easier to test and reuse.

**Test it:** Verify that the transfer creation still work exactly as before.

### 5. Implement Currency Drop-downs for Exchange Rate Calculator
Replace the text input fields for currencies with drop-down menus that only show valid currency pairs. This improves user experience by preventing invalid currency selections.

**What you need to do:**

**5.a. Create endpoint for available source currencies**

**5.b. Create endpoint for available target currencies**

**5.c. Update the HTML to use drop-downs**

**Test it:**
1. Open the exchange rate calculator page
2. The source currency drop-down should show all available currencies
3. Select a source currency - the target drop-down should update to show only valid pairs
4. Perform a conversion and verify it still works

### Extra exercises
If you would still have some time after finishing these tasks, there are a couple things our application could use:
- tests for the application code
- some pages do not have proper validation and error handling - you could add that and display error messages on the UI, e.g. 
  - transfer could also have currency drop down
  - for transfer creation, recipient could also be loaded form a list
