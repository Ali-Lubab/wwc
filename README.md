# Getting started

## What do you need?
- Java
- Intellij - hopefully you're already in there :)
- Install Lombok plugin in Intellij and enable annotation processing in settings
- You can use AI tools, but don't use Agentic AIs that can write code for you without your input

## Basic commands
- `./gradlew bootRun` starts spring boot application. You can access the UI by opening http://localhost:8080 in your web browser.
- `./gradlew clean` clears the build directory.
- `./grardlew build` builds the project.
- `--info` at the end of gradle commands shows more detailed logs.
- `ctrl` + `c` in the terminal exits running command.

Alternatively, if you want to run the application from Intellij, you can open `WwcApplication.java` and click the green play button next to the `main` method.
![run-application.png](docs/images/run-application.png)

If you change something on the backend code, you need to stop the application and start it again for the changes to take effect.

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

### 2. Add Email Field to Recipient
Extend the Recipient entity to store email addresses, allowing users to associate contact information with their recipients.

### 3. Implement Transfer Creation with Balance Updates
Complete the money transfer functionality so that when a transfer is created, the sender's balance is deducted appropriately.

### 4. Refactor Transfer Creation Logic into a Service
Extract the transfer creation business logic from controllers into a dedicated service class. This follows the single responsibility principle and makes the code more maintainable and testable.

**Why this matters:** Controllers should only handle HTTP requests/responses. Business logic (like currency conversion calculations) belongs in service classes. This separation makes code easier to test and reuse.

### 5. Implement Currency Drop-downs for Exchange Rate Calculator
Replace the text input fields for currencies with drop-down menus that only show valid currency pairs. This improves user experience by preventing invalid currency selections.

**What you need to do:**

**5.a. Create endpoint for available source currencies**

**5.b. Create endpoint for available target currencies**

**5.c. Update the HTML to use drop-downs**

### Extra exercises
If you would still have some time after finishing these tasks, there are a couple more things our application could use:
- tests for the application code
- some pages do not have proper validation and error handling - you could add that and display error messages on the UI, e.g. 
  - transfer could also have currency drop down
  - for transfer creation, recipient could also be loaded form a list
