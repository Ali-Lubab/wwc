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