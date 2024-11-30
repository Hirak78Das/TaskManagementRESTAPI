package methods;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import database.DatabaseManager;
import model.Task;
import model.Task.Priority;
import model.Task.Status;

public class Queries {

  // insert into table --> used while creating a task
  public static void Insert(String title, String description, Priority priority, Connection connection)
      throws SQLException {
    String query = "INSERT INTO tasks (title, description, priority) VALUES (?, ?, ?)";
    try (
        PreparedStatement pstmt = connection.prepareStatement(query)) {
      pstmt.setString(1, title);
      pstmt.setString(2, description);
      pstmt.setString(3, priority.toString());
      pstmt.executeUpdate();
    }
  }

  // fetch all tasks from the database
  public static List<Task> Select() throws SQLException {
    String query = "SELECT * FROM tasks";
    List<Task> tasks = new ArrayList<>();
    try (Connection connection = DatabaseManager.getConnection();
        Statement stmt = connection.createStatement()) {
      ResultSet result = stmt.executeQuery(query);

      tasks.clear(); // clear all in-memory tasks to prevent appending duplicate tasks from the
                     // database
      while (result.next()) {
        tasks.add(new Task(
            result.getInt("id"), // column label --> id
            result.getString("title"),
            result.getString("description"),
            Priority.valueOf(result.getString("priority")), // convert the string to enum
            Status.valueOf(result.getString("status")),
            result.getString("created_at"),
            result.getString("updated_at")));
      }
      if (tasks.isEmpty()) {
        return List.of(); // returns empty list
      }
    }
    return tasks;
  }

  // get created_time and updated_time from the server;
  public static String[] getTime(int id) throws SQLException {
    String query = "SELECT created_at, updated_at FROM tasks where id = ?";

    try (Connection connection = DatabaseManager.getConnection();
        PreparedStatement stmt = connection.prepareStatement(query)) {
      stmt.setInt(1, id);

      String[] time = new String[2];
      try (ResultSet rs = stmt.executeQuery()) {
        if (rs.next()) {
          time[0] = rs.getString("created_at");
          time[1] = rs.getString("updated_at");
        }
        return time;
      }
    }
  }

  // get task with id
  public static Task selectTaskById(int id) throws SQLException {
    String query = "SELECT * FROM tasks WHERE id = ?";
    try (Connection connection = DatabaseManager.getConnection();
        PreparedStatement stmt = connection.prepareStatement(query)) {
      stmt.setInt(1, id);
      try (ResultSet rs = stmt.executeQuery()) {
        if (rs.next()) {
          // Assuming you have a Task constructor or builder to create an object from
          // ResultSet
          return new Task(
              rs.getInt("id"),
              rs.getString("title"),
              rs.getString("description"),
              Priority.valueOf(rs.getString("priority")),
              Task.Status.valueOf(rs.getString("status")),
              rs.getString("created_at"),
              rs.getString("updated_at"));
        }
      }
    }
    return null; // No task found
  }

  // update any task in database
  public static void updateTask(int id, String title, String description, Priority priority) throws SQLException {
    String query = "UPDATE tasks SET title = ?, priority = ?, description = ?, status = 'PENDING' WHERE id = ?";
    try (Connection connection = DatabaseManager.getConnection();
        PreparedStatement pstmt = connection.prepareStatement(query)) {
      pstmt.setString(1, title);
      pstmt.setString(2, priority.toString());
      pstmt.setString(3, description);
      pstmt.setInt(4, id);

      pstmt.executeUpdate();
    }
  }

  // delete any task in database
  public static void deleteTask(int id) throws SQLException {
    String query = "DELETE FROM tasks WHERE id = ?";
    try (Connection connection = DatabaseManager.getConnection();
        PreparedStatement pstmt = connection.prepareStatement(query)) {
      pstmt.setInt(1, id);
      pstmt.executeUpdate();
    }
  }

  // change task status to IN_PROGRESS in the database
  public static void Updatestatus(int id) throws SQLException {
    String query = "UPDATE tasks SET status= 'IN_PROGRESS' WHERE id = ?";
    try (Connection connection = DatabaseManager.getConnection();
        PreparedStatement pstmt = connection.prepareStatement(query)) {
      pstmt.setInt(1, id);
      pstmt.executeUpdate();
    }
  }

  // change task status to COMPLETED in the database
  public static void UPDATEstatus(int id) throws SQLException {
    String query = "UPDATE tasks SET status= 'COMPLETED' WHERE id = ?";
    try (Connection connection = DatabaseManager.getConnection();
        PreparedStatement pstmt = connection.prepareStatement(query)) {
      pstmt.setInt(1, id);

      pstmt.executeUpdate();
    }
  }
}
