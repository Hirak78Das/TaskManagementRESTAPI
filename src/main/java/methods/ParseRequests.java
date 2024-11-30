package methods;

import model.Task;
import service.TaskService;

public class ParseRequests {

  private static TaskService taskService = new TaskService();

  // Method to parse JSON and create a Task object for handlePostTasks() method
  public static Task parseTask(String json) {
    // Assuming simple JSON format: {"title": "...", "description": "...",
    // "priority": "..."}
    String title = json.split("\"title\": \"")[1].split("\"")[0];
    String description = json.split("\"description\": \"")[1].split("\"")[0];
    int priorityNO = Integer.parseInt(json.split("\"priority\": ")[1].split("}")[0].trim());

    return taskService.createTask(title, description, priorityNO);
  }

  // Method to parse JSON and to update the task object
  public static boolean parseTaskUpdate(String json) {
    // Assuming simple JSON format: {"title": "...", "description": "...",
    // "priority": "..."}
    String title = json.split("\"title\": \"")[1].split("\"")[0];
    String description = json.split("\"description\": \"")[1].split("\"")[0];
    int id = Integer.parseInt(json.split("\"ID\": ")[1].split(",")[0].trim());
    int priority = Integer.parseInt(json.split("\"priority\": ")[1].split("}")[0].trim());
    if (taskService.updateTask(id, title, description, priority)) {
      return true;
    }
    return false;
  }

}
