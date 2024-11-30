// ----- store data into the database server -------

package service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import database.DatabaseManager;
import methods.ConvertPriority;
import methods.Queries;
import model.Task;
import model.Task.Priority;
import model.Task.Status;

public class TaskService {

  // private List<Task> tasks = new ArrayList<>();
  // private int currentID;

  // method to create a task
  public Task createTask(String title, String description, int priorityNO) {
    Priority priority = ConvertPriority.enumPriority(priorityNO);
    try (Connection connection = DatabaseManager.getConnection()) {
      Queries.Insert(title, description, priority, connection);
      // fetch the auto gennerated id created in mysql, so that you can get any task
      // later
      // by using the id
      try (
          Statement stmt = connection.createStatement();
          ResultSet result = stmt.executeQuery("SELECT LAST_INSERT_ID()")) {
        int currentID = -1;
        if (result.next()) { // result.next() checks each row if there is a column
          currentID = result.getInt(1); // the first column of that row
        }
        String[] time = Queries.getTime(currentID);

        return new Task(currentID, title, description, priority, Status.valueOf("PENDING"), time[0], time[1]); // convert
                                                                                                               // to
                                                                                                               // enum

      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return null; // if task in not created
  }

  // fetch all available tasks from database
  public List<Task> getAllTasks() {

    try {
      List<Task> tasks = Queries.Select();
      if (!tasks.isEmpty()) {
        return tasks;
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return List.of();
  }

  // start a task (change status to IN_PROGRESS)
  public int startTask(int id) {

    try {
      Task task = Queries.selectTaskById(id);
      if (task != null) {
        if (task.getId() == id && task.getStatus() == Task.Status.PENDING) {
          // make IN_PROGRESS in the database
          Queries.Updatestatus(id);
          return 1;
        } else if (task.getId() == id && task.getStatus() == Task.Status.COMPLETED) {
          return 2;
        }
      }

    } catch (SQLException e) {
      e.printStackTrace();
    }
    return -1;
  }

  // when task is completed (change status to COMPLETED)
  public int completeTask(int id) {
    try {
      Task task = Queries.selectTaskById(id);
      if (task != null) {
        if (task.getId() == id && task.getStatus() == Task.Status.IN_PROGRESS) {
          // make COMPLETED in the database;
          Queries.UPDATEstatus(id);
          return 1;
        } else if (task.getId() == id && task.getStatus() == Task.Status.PENDING) {
          return 2;
        } else if (task.getId() == id && task.getStatus() == Task.Status.COMPLETED) {
          return 3;
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return -1; // if id is not found
  }

  // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ Approach I ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

  // find a task by id
  public Task getTaskByID(int id) {
    try {
      // fetch tasks from database
      Task task = Queries.selectTaskById(id);
      if (task != null) {
        return task;
      }
    } catch (SQLException e) {
      e.printStackTrace();
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
  public boolean updateTask(int id, String title, String description, int priorityNO) {

    Priority priority = ConvertPriority.enumPriority(priorityNO);

    Task task = getTaskByID(id);
    try {
      if (task != null) {
        Queries.updateTask(id, title, description, priority);
        return true;
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return false;
  }

  // Deleta a task
  public boolean deleteTask(int id) {
    try {
      // first check if the task is present for the specified getId
      if (Queries.selectTaskById(id) != null) {
        // delete the task
        Queries.deleteTask(id);
        return true;
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return false;
  }

}
