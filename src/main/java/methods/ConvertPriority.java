package methods;

import model.Task.Priority;

public class ConvertPriority {

  // change int priorityno to enum

  public static Priority enumPriority(int priorityNO) {
    if (priorityNO == 1) {
      return Priority.HIGH;

    } else if (priorityNO == 2) {
      return Priority.MEDIUM;
    }
    return Priority.LOW;
  }
}
