package methods;

import java.util.Scanner;

public class ParseInteger {

  private static Scanner sc = null;

  public static int InputValidNumber() {
    ParseInteger.sc = new Scanner(System.in);
    int id;
    while (true) {
      try {
        id = Integer.parseInt(sc.nextLine());
        break;
      } catch (NumberFormatException e) {
        System.out.println(" \n        invalid integer: " + e.getMessage());
        System.out.print("\n             enter id again : ");
      }
    }
    return id;
  }
}
