package model;

public class Task {

  // enums are public static and final(cant overriden) implicitly
  public enum Status {
    // constant values
    PENDING,
    IN_PROGRESS,
    COMPLETED
  }

  private int id; // unique id for each task
  private String title; // short title of the task
  private String description;
  private Status status; // declaring a variable of type Status to hold one of the constant, not making
                         // any instance here
  private int priority; // E.g. 1 = High, 2 = Medium, 3 = Low

  public Task(int id, String title, String description, int priority) {
    this.id = id;
    this.title = title;
    this.description = description;
    this.priority = priority;
    this.status = Status.PENDING; // Default status on creation
  }

  //
  public Status getStatus() {
    return status;
  }

  public void setStatus(Status status) {
    this.status = status;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getDescription() {
    return description;
  }

  public void setDesription(String description) {
    this.description = description;
  }

  public int getPriority() {
    return priority;
  }

  public void setPriority(int priority) {
    this.priority = priority;
  }

  @Override
  public String toString() {
    return "Task { " + "id = " + id + ", title = " + title + ", description = " + description + ", status = " + status
        + ",priority = " + priority + " }";
  }

}
