package service;

import java.util.ArrayList;
import java.util.List;

import model.Task;

public class TaskService {

  private List<Task> tasks = new ArrayList<>();
  private int currentID = 1;

  // create a task
  public Task createTask(String title, String description, String status, int priority) {
    Task task = new Task(currentID++, title, description, status, priority);
    tasks.add(task);
    return task;
  }

  // see all available tasks
  public List<Task> getAllTasks() {
    return tasks;
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
  public boolean updateTask(int id, String title, String description, String status, int priority) {

    Task task = getTaskByID(id);

    if (task != null) {
      task.setTitle(title);
      task.setStatus(status);
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
