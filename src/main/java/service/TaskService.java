// ----- store data into the database server -------

package service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import database.DatabaseManager;
import model.Task;
import model.Task.Status;

public class TaskService {

  private List<Task> tasks = new ArrayList<>();
  // private int currentID;

  // create a task
  public Task createTask(String title, String description, int priority) {

    String sql = "INSERT INTO task (title, description, priority, status) VALUES (?, ?, ?, 'PENDING')";
    try (
        Connection connection = DatabaseManager.getConnection();
        PreparedStatement pstmt = connection.prepareStatement(sql)) {
      pstmt.setString(1, title);
      pstmt.setString(2, description);
      pstmt.setInt(3, priority);
      pstmt.executeUpdate();

      // fetch the auto gennerated id created by mysql, so that you can get any task
      // later
      // by using the id
      try (Statement stmt = connection.createStatement();
          ResultSet result = stmt.executeQuery("SELECT LAST_INSERT_ID()")) {
        if (result.next()) { // result.next() checks each row if there is a column
          int currentID = result.getInt(1); // the first column of that row
          Task task = new Task(currentID, title, description, priority);
          tasks.add(task);
          return task;
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return null; // if task in not created
  }

  // see all available tasks
  public List<Task> getAllTasks() {
    return tasks;
  }

  // start a task (change status to IN_PROGRESS)
  public boolean startTask(int id) {
    Task task = getTaskByID(id);
    if (task != null && task.getStatus() == Task.Status.PENDING) {
      task.setStatus(Task.Status.IN_PROGRESS);
      return true;
    }
    return false;
  }

  // when task in completed (change status to COMPLETED)
  public boolean completeTask(int id) {
    Task task = getTaskByID(id);
    if (task != null && task.getStatus() == Task.Status.IN_PROGRESS) {
      task.setStatus(Task.Status.COMPLETED);
      return true;
    }
    return false;
  }

  // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ Approach I ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

  // find a task by id
  public Task getTaskByID(int id) {
    for (Task task : tasks) {
      if (task.getId() == id) {
        return task;
      }
    }
    return null; // need to handle this null value by caller
  }

  // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ Approach II ~~~~~~~~~~~~~~~~~~~~~~~~~~~~

  // using lambda expression find specific task
  /*
   * public Task getTaskByID(int id){
   * return tasks.stream().filter(task -> task.getId() ==
   * id).findFirst().orElse(null);
   * }
   * 
   */

  // update a task
  public boolean updateTask(int id, String title, String description, int priority) {

    Task task = getTaskByID(id);

    if (task != null) {
      task.setTitle(title);
      task.setStatus(Status.PENDING);
      task.setPriority(priority);
      task.setDesription(description);
      return true;
    }
    return false;
  }

  // Deleta a task
  public boolean deleteTask(int id) {

    Task task = getTaskByID(id);

    if (task != null) {
      tasks.remove(task);
      return true;
    }
    return false;
  }
}
