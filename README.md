# Getting started

## What do you need?
- Java
- Intellij - hopefully you're already in there :)
- Install Lombok plugin in Intellij and enable annotation processing in settings

## Basic commands
- `./gradlew bootRun` starts spring boot application

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

### 1. Modify the ``Recipient`` entity to include a new field called ``email`` of type ``String``. 
Ensure that this field is properly mapped to the database and can be used for storing the recipient's email address.
Ensure that the appropriate service fetches the data from database.
The field should be displayed in the recipient list and should be included in the recipient details view.

### 2.  Implement transfer creation functionality in the application.
Implement the logic in ``TransferController`` that converts source currency to target currency using the exchange rate from RateRepository.

### 3. Implement Exchange Rate Calculator
Implement a functionality that calculates the exchange rate between two currencies. 
This service should fetch the latest exchange rates from RateRepository and perform the necessary calculations to determine the exchange rate between the specified source and target currencies.
Implement simple error handling for cases where the exchange rate is not available or if there are issues with fetching the data from the repository.

### 4. Refactoring exchange rate business logic
``TransferController`` and ``RateController`` currently contain business logic related to exchange rate calculations. 
Refactor the code to move this logic into a separate service class, such as ``ExchangeRateService``.
