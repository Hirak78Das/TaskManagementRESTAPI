//  ~~~~~~~~~~~~~`Implement JDBC to code ~~~~~~~~~~~~~~`

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBCdemo {

  public static void main(String[] args) {

    try (
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb", "root",
            "Hirak_602223!");
        Statement stmt = connection.createStatement()) {

      ResultSet resultSet = stmt.executeQuery("SELECT * FROM designation");
      int code;
      String title;
      while (resultSet.next()) {
        code = resultSet.getInt("code");
        title = resultSet.getString("title").trim();
        System.out.println("Code : " + code
            + " Title : " + title);
      }
      resultSet.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
}
