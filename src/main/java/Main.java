
import model.Task;
import service.TaskService;
import java.util.Scanner;

public class Main {
  private static Scanner sc = new Scanner(System.in); // static to use without making instances

  private static TaskService taskService = new TaskService();

  public static void main(String args[]) {

    // Menu Options
    System.out.println("\n  ~~~~~~~~~~~~~~~~~ TASK MANAGEMENT ~~~~~~~~~~~~~~~~~");
    System.out.println("                          Menu                         ");
    System.out.println("                     1. Create Task");
    System.out.println("                     2. View all Tasks");
    System.out.println("                     3. Find task by ID");
    System.out.println("                     4. update Task");
    System.out.println("                     5. delete Task");
    System.out.println("                     6. Exit");

    while (true) {
      System.out.print("\n       choose an option : ");
      String input = sc.nextLine();
      int choice = Integer.parseInt(input);
      switch (choice) {
        case 1:

          // create task
          System.out.print("  Enter title: ");
          String title = sc.nextLine();
          System.out.print("  Enter description: ");
          String description = sc.nextLine();
          System.out.print("  Enter status: ");
          String status = sc.nextLine();
          System.out.print("  Enter priority (1=High, 2=Medium, 3=Low): ");
          int priority = sc.nextInt();
          sc.nextLine(); // Consume newline
          taskService.createTask(title, description, status, priority);
          break;
        case 2:
          // display all tasks
          for (Task task : taskService.getAllTasks()) {
            System.out.println(task);

          }
          break;
        case 3:
          System.out.print("\n    Enter Task id:");
          String s = sc.nextLine();
          int id = Integer.parseInt(s);
          Task taskId = taskService.getTaskByID(id);
          if (taskId == null) {
            System.out.println("\n    wrong id!!");
            break;
          }
          System.out.println("\n" + taskId);
          break;

        case 4:
          // update task
          System.out.print("  Enter title: ");
          String titl = sc.nextLine();
          System.out.print("  Enter description: ");
          String des = sc.nextLine();
          System.out.print("  Enter status: ");
          String stat = sc.nextLine();
          System.out.print("  Enter priority (1=High, 2=Medium, 3=Low): ");
          int prior = sc.nextInt();
          System.out.print("  Enter id of the task to be updated: ");
          int ID = sc.nextInt();
          sc.nextLine(); // Consume newline

          boolean updated = taskService.updateTask(ID, titl, des, stat, prior);
          System.out.println("\nUpdate Task 1 : " + (updated ? "Success" : "Failure"));
          break;
        case 5:
          // delete task
          System.out.println("Enter task id to delete: ");
          boolean deleted = taskService.deleteTask(sc.nextInt());
          sc.nextLine(); // consume newline present in the console buffer
          System.out.println("\nDeleted Task 2 : " + (deleted ? "Success" : "Failure"));

          break;
        case 6:
          return;

        default:
          System.out.println("\n         please enter no from 1 - 6 !! ");
          break;
      }
    }

  }
}
