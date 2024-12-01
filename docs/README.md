## Task Manager CLI Application

### About

The Task Manager CLI Application is a user-friendly command-line tool that allows users to manage tasks efficiently. It interacts with a database-backend server, enabling users to create, read, update, and delete tasks seamlessly. Designed to showcase the fundamentals of backend development, this application serves as a comprehensive introduction to Java-based backend systems.

### Purpose

The primary purpose of this project is to learn and implement core backend development principles using Java. By building this application, I have gained hands-on experience with:

1. HTTP Communication:
   Understanding how to send HTTP requests (e.g., GET, POST, PUT, DELETE) from a client application to a server.
   Handling server responses effectively.

2. Server-Side Logic:
   Designing a lightweight HTTP server to process client requests and perform corresponding operations.
   Implementing RESTful principles for resource management.

3. Database Integration:
   Setting up and managing a relational database (MySQL) to store and fetch application data.
   Writing SQL queries for CRUD operations.
   Integrating the database with Java using JDBC.

4. CLI Interaction:
   Creating a command-line interface to make task management intuitive and efficient.
   Allowing users to perform operations like adding tasks, viewing task lists, editing tasks, and deleting tasks.

### Required Tools

Firstly, install these tools to run the program:

Note: This project does not use Maven or Gradle for building the program. All dependencies are managed manually.

- **Java 17 or higher**
  Download from the [Oracle Java Downloads page](https://www.oracle.com/java/technologies/downloads/).
- **MySQL Server**
  Download from the [MySQL Installer page](https://dev.mysql.com/downloads/installer/).
- **MySQL Connector JAR**
  Download the MySQL Connector JAR file to enable Java to connect to the MySQL server.
  Download from the [MySQL Connector/J page](https://dev.mysql.com/downloads/connector/j/). Choose the "Platform Independent" version.

Note: Delete the JAR file currently included in this project to avoid version conflicts and put your jar file in the lib/.

### **Run Application**:

To run the application:

- **Start the server program in one terminal window --> after starting the server program, it continuously listens for client requests and responses accordingly**
- **Open another terminal window to run the client program, which will communicate with the server to perform task management operations via http requests.**

NOTE: Both the server and client programs must be running simultaneously to enable full functionality.

1. ##### Clone the repository

```
  git clone git@github.com:Hirak78Das/TaskManagementRESTAPI.git
```

2. ##### Connect to your MySQL database:
   Navigate to the root directory of the project

```
cd TaskManagementRESTAPI/
```

Enter your mysql username and password in this file and save the file:

```
nvim src/main/java/database/DatabaseManager.java
```

3. ##### Compile the java files:

```
javac -cp lib/mysql-connector-j-9.1.0.jar src/main/java/**/*.java
```

4. ##### Create a database in your MySQL server:
   Run the CreateDatabase program:

```
java -cp lib/mysql-connector-j-9.1.0.jar:src/main/java/ CreateDatabase

```

5. ##### Run the Main program:

```
java -cp lib/mysql-connector-j-9.1.0.jar:src/main/java MainClient
```

6. #### Start the server:

   Open another terminal window and run the server program. This will host the backend service locally.

   ```
   java -cp lib/mysql-connector-j-9.1.0.jar:src/main/java httpServer/Server
   ```
