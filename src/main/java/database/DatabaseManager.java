package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseManager {
  private static final String BASE_URL = "jdbc:mysql://localhost:3306"; // to create the database use the BASE_URL
  private static final String Database_name = "/TaskManagementDB";
  private static final String URL = BASE_URL + Database_name; // used this url after the database is created in mysql
  // server

  private static final String USER = "root"; // mysql username
  private static final String PASSWORD = "Hirak_602223!"; // mysql PASSWORD

  private static Connection connect;

  // connect to the mysql server to create the database
  public static Connection getBaseConnection() throws SQLException {
    return DriverManager.getConnection(BASE_URL, USER, PASSWORD);
  }

  // connect after the database has been created
  public static Connection getConnection() throws SQLException {
    if (connect == null || connect.isClosed()) {
      connect = DriverManager.getConnection(URL, USER, PASSWORD);
    }
    return connect;
  }
}
