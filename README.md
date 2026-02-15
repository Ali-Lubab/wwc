# Getting started

## What do you need?
- Java
- Intellij - hopefully you're already in there :)
- Install Lombok plugin in Intellij and enable annotation processing in settings

## Basic commands
- `./gradlew bootRun` starts spring boot application.
- `./gradlew clean` clears the build directory.
- `./grardlew build` builds the project.
- `--info` at the end of gradle commands shows more detailed logs.
- `ctrl` + `c` in the terminal exits running command.

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

## Tasks
This is a money transfer application. The aim is to be able to create a recipient, check currency exchange rates and create a transfer. 

### 1. Implement Exchange Rate Calculator
Build a currency conversion feature that allows users to calculate how much money they will receive when exchanging between currencies.

**Test it:** Open `exchange-rate-calculator.html` in the browser and try converting 100 EUR to USD.

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

### 4. Refactor Exchange Rate Logic into a Service
Extract the exchange rate business logic from controllers into a dedicated service class. This follows the single responsibility principle and makes the code more maintainable and testable.

**Why this matters:** Controllers should only handle HTTP requests/responses. Business logic (like currency conversion calculations) belongs in service classes. This separation makes code easier to test and reuse.

**Test it:** Verify that the exchange rate calculator and transfer creation still work exactly as before.

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