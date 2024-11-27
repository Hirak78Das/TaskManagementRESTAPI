import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.Statement;

import database.DatabaseManager;

public class CreateDatabase {
  public static void main(String args[]) {
    try (Connection baseConn = DatabaseManager.getBaseConnection();
        Statement stmt = baseConn.createStatement()) {
      // read the sql file
      String initSql = Files.readString(Path.of("src/main/mysql/init.sql"));

      // split the sql script into separate commands
      String[] sqlCommands = initSql.split("(?<=;)\s*"); // ignore blank space
      stmt.execute(sqlCommands[0]);
      stmt.execute(sqlCommands[1]);
      stmt.execute(sqlCommands[2]);
      System.out.println("Database created!!");
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
