
import java.util.Scanner;

import methods.Menu;

public class MainClient {

  private static Scanner sc = new Scanner(System.in); // static to use without making instances

  // show menu
  public static void showMenu() {
    // Menu Options
    System.out.println("\n  ⭐ ⭐ ⭐ ⭐ ⭐ ⭐ ⭐ ⭐ ⭐ ⭐ TASK MANAGEMENT  ⭐ ⭐ ⭐ ⭐ ⭐ ⭐ ⭐ ⭐ ⭐ ⭐ ⭐");
    System.out.println("\n                      ⭐             Menu                      ⭐");
    System.out.println("                      ⭐      1. Create Task                   ⭐");
    System.out.println("                      ⭐      2. View all Tasks                ⭐");
    System.out.println("                      ⭐      3. Find task by ID               ⭐");
    System.out.println("                      ⭐      4. update Task                   ⭐");
    System.out.println("                      ⭐      5. delete Task                   ⭐");
    System.out.println("                      ⭐      6. Start Task                    ⭐");
    System.out.println("                      ⭐      7. Complete Task                 ⭐");
    System.out.println("                      ⭐      8. show Menu                     ⭐");
    System.out.println("                      ⭐      9. Exit                          ⭐");

    System.out.println("\n             🌟  🌟 🌟 🌟 🌟 🌟 🌟 🌟 🌟 🌟 🌟 🌟 🌟 🌟 🌟 🌟 🌟 🌟 🌟 🌟 🌟");

  }

  public static void menu() {

    while (true) {
      System.out.print("\n       choose an option : ");
      String input = sc.nextLine();
      int choice = Integer.parseInt(input);
      switch (choice) {

        case 1:
          Menu.createTask(); // craete task and store in the database server
          break;

        case 2:
          Menu.FetchTasks(); // get the tasks from the server
          break;

        case 3:
          // get task with specified id from server
          Menu.FetchTaskWithId();
          break;

        case 4:
          // update task
          Menu.updateTask();
          break;

        case 5:
          // delete task
          Menu.DeleteTask();
          break;

        case 6:
          // start task
          Menu.StartTask();
          break;

        case 7:
          // complete task
          Menu.completeTask();
          break;

        case 8:
          showMenu();
          break;

        case 9:
          // exit
          Menu.close();
          return;

        default:
          System.out.println("\n         please enter no from 1 - 8  🐷 ");
          break;
      }
    }
  }

  public static void main(String args[]) {
    showMenu();
    menu();
  }
}
