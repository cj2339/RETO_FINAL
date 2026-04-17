# 🚀 Project: Imperial Maritime

## 📝 Description

**Imperial Maritime** is a robust desktop application designed for the comprehensive management of a cruise line company. Developed in Java, this application allows administrators to efficiently manage cruises, clients, bookings, and administrator information. Its architecture follows the Model-View-Controller (MVC) pattern and utilizes the Data Access Object (DAO) pattern for clean and modular interaction with the database. It includes advanced features such as booking discount management through stored procedures and an intuitive user interface.

---

## 🧰 Technologies Used

*   **Java JDK 21**
*   **Swing** (for the Graphical User Interface)
*   **WindowBuilder** (for visual interface design in Eclipse)
*   **🗄️ MySQL 8.0**
*   **🔌 JDBC** (MySQL connector for database connectivity)
*   **Mockito** (for unit testing)
*   **JUnit 5** (testing framework)

**Tools:**

*   **Eclipse IDE** (primary development environment)
*   **MySQL Workbench** (for database management)

---

## 📚 Dependencies

This project uses the following external libraries:

*   MySQL JDBC Connector (e.g., `mysql-connector-java-8.0.15.jar`)
*   Mockito Libraries (e.g., `mockito-core-5.23.0.jar`, `mockito-junit-jupiter-5.23.0.jar`)
*   JUnit 5 Libraries (e.g., `junit-jupiter-api-5.10.0.jar`, `junit-jupiter-engine-5.10.0.jar`)
*   JCalendar (e.g., `jcalendar-1.4.jar`)

**Make sure to add the .jar files to the project in Eclipse:**

1.  Right-click on your project.
2.  Select `Properties`.
3.  Go to `Java Build Path`.
4.  In the `Libraries` tab, click `Add External JARs...` and select all the mentioned `.jar` files.

---

## 🗄️ Database

The project includes the database script: `imperialMaritime.sql`

### ▶️ How to Use It

1.  Create a database in MySQL (e.g., `imperialmaritime`).
2.  Import the `imperialMaritime.sql` file.

**a. From the console:**

```bash
mysql -u your_user -p your_database_name < imperialMaritime.sql
```

*(Replace `your_user` with your MySQL username and `your_database_name` with the name of the database you created.)*

**b. Or using a graphical tool like MySQL Workbench:**

1.  Open MySQL Workbench.
2.  Connect to your MySQL instance.
3.  Go to `Server` > `Data Import`.
4.  Select `Import from Self-Contained File` and choose `imperialMaritime.sql`.
5.  Ensure the correct target schema is selected.

---

## 📦 Installation

### 📥 Clone the Repository

```bash
git clone https://github.com/cj2339/RETO_FINAL.git
```

### 📁 Import the Project into Eclipse

1.  Open Eclipse IDE.
2.  Go to `File` > `Import...`.
3.  Select `General` > `Existing Projects into Workspace` and click `Next`.
4.  Click `Browse...` next to `Select root directory:` and navigate to the folder where you cloned the repository (`RETO_FINAL`).
5.  Ensure the `RETO_FINAL` project is selected and click `Finish`.

---

## ▶️ Execution

Once the project is imported into Eclipse and the JAR dependencies are configured, you can run the application:

1.  **Configure the database connection:**
    *   Open the file `src/configClass.properties`.
    *   Ensure the values for `Conn`, `DBUser`, and `DBPass` match your MySQL configuration.
2.  **Run the application:**
    *   Open the class `src/main/Main.java`.
    *   Right-click in the editor and select `Run As` > `Java Application`.

---

## 🧪 Tests

The project includes unit and integration tests for the DAO layers, using **JUnit 5** and **Mockito**.

### Test Coverage:

*   **`TestDBImplementationAdministrator.java`**: Contains full integration tests for the administrator CRUD (insert, get, update, delete, check existence). These tests interact directly with the database.
*   **`TestDBImplementationCruise.java`**: Implements unit tests for the cruise CRUD. **Uses Mockito** to mock the database connection (JDBC), `PreparedStatement`, and `ResultSet`, allowing the DAO logic to be tested in isolation without depending on a real database.
*   **`TestDBImplementationClient.java`**: Contains full integration tests for the client CRUD (insert, get, update, delete, check existence). These tests interact directly with the database.
*   **`TestDBImplementationWorker.java`**: Contains full integration tests for the worker CRUD (insert, get, update, delete, check existence). These tests interact directly with the database.

### To run them in Eclipse:

1.  Ensure the JUnit 5 and Mockito JARs are correctly added to the project libraries (see `📚 Dependencies` section).
2.  Navigate to the `test/model` folder.
3.  Right-click on any of the `TestDBImplementation*.java` files or the `test/model` folder.
4.  Select `Run As` > `JUnit Test`.

The tests verify the correct interaction with the database and the business logic implemented in the DAOs.
