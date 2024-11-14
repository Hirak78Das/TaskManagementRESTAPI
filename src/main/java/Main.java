
import model.Task;
import service.TaskService;

public class Main {
  public static void main(String args[]) {

    TaskService taskService = new TaskService();

    // create task
    taskService.createTask("Task 1", "Description", "Pending", 1);
    taskService.createTask("Task 2", "Description", "In-progress", 2);

    // display all tasks
    for (Task task : taskService.getAllTasks()) {
      System.out.println(task);
    }

    // update task
    boolean updated = taskService.updateTask(1, "Updated Task 1", "updated Description", "Completed", 1);
    System.out.println("\nUpdate Task 1 : " + (updated ? "Success" : "Failure"));

    // display tasks after update
    System.out.println("\nupdated tasks");
    for (Task task : taskService.getAllTasks()) {
      System.out.println(task);
    }

    // delete task
    boolean deleted = taskService.deleteTask(2);
    System.out.println("\nDeleted Task 2 : " + (deleted ? "Success" : "Failure"));

    // display task after deletion
    System.out.println("\nremaining tasks : ");
    taskService.getAllTasks().forEach(task -> System.out.println(task));
  }
}
