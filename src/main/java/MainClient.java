
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
      String input = null;
      try {
        input = sc.nextLine();
        if (input.equals(null) || input.equals("")) {
          System.out.println("\n   Input is empty!!");
          continue;
        }
        int choice = Integer.parseInt(input);

        if (choice < 1 || choice > 9) {
          System.out.println("\n         choose between 1 - 9  🐷 ");
          continue;
        }

        switch (choice) {
          case 1:
            Menu.createTask();
            break;
          case 2:
            Menu.FetchTasks();
            break;
          case 3:
            Menu.FetchTaskWithId();
            break;
          case 4:
            Menu.updateTask();
            break;
          case 5:
            Menu.DeleteTask();
            break;
          case 6:
            Menu.StartTask();
            break;
          case 7:
            Menu.completeTask();
            break;
          case 8:
            showMenu();
            break;
          case 9:
            Menu.close();
            return;
        }
      } catch (NumberFormatException e) {
        System.out.println("\n   Invalid input: '" + input + "'. Please enter a number between 1 and 9.");
      }
    }
  }

  public static void main(String args[]) {
    showMenu();
    menu();
  }

}
