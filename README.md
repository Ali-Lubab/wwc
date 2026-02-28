# Getting started

I you want to run the application from Intellij, you can open `WwcApplication.java` and click the green play button next to the `main` method.
![run-application.png](docs/images/run-application.png)

If you change something on the backend code, you need to stop the application and start it again for the changes to take effect.

## Useful Intellij shortcuts
- In `.java` files, hold `command` button on your keyboard and click on a variable name to go to the declaration.
- Use `command` + `option` + left/right arrow to go back where you were before or move forward again.
- Double shift to search for files, classes, symbols or even actions (e.g. "run application" or "reformat code").

## Useful debugging tips
Use Chrome. Run the backend application first, then head over http://localhost:8080 in your web browser to open the frontend. 
Open Chrome DevTools (right click anywhere on the page -> Inspect) and go to the "Network" tab. You will see all the network requests that the frontend is making to the backend. 
You can click on any request to see details about it, including the request payload and the response from the server. This is very useful for debugging issues with API calls.

## Tasks
This is a money transfer application. The aim is to be able to create a recipient, check currency exchange rates and create a transfer. 
There is a `HINTS.md` file which has more help and details about the exercise. If you would like to do it yourself, we suggest not to read it first, only if you are stuck and need some help.

### 1. Implement Exchange Rate Calculator
Build a currency conversion feature that allows users to calculate how much money they will receive when exchanging between currencies.
The frontend is done already, but the backend implementation is missing. Check the debugging tips to see what is called on the backend.

### 2. Add Email Field to Recipient
Extend the Recipient entity to store email addresses, allowing users to associate contact information with their recipients.
The frontend has everything already, but we need to make sure the backend is also aware of the `email` field and can store it / retrieve from in the database.

### 3. Implement Transfer Creation with Balance Updates
Complete the money transfer functionality so that when a transfer is created, the sender's balance is deducted appropriately.
Think through when a transfer happens, what are the operations which need to happen. git 

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
