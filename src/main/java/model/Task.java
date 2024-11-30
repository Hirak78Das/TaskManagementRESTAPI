package model;

public class Task {

  // enums are public static and final(cant overriden) implicitly
  public enum Status {
    // constant values
    PENDING,
    IN_PROGRESS,
    COMPLETED
  }

  public enum Priority {
    LOW,
    MEDIUM,
    HIGH
  }

  private int id; // unique id for each task
  private String title; // short title of the task
  private String description;
  private Status status; // declaring a variable of type Status to hold one of the constant, not making
                         // any instance here
  private Priority priority;
  private String created_at;
  private String updated_at;

  public Task(int id, String title, String description, Priority priority, Status status, String created_at,
      String updated_at) {
    this.id = id;
    this.title = title;
    this.description = description;
    this.priority = priority;
    this.status = status; // PENDING by default on database
    this.created_at = created_at;
    this.updated_at = updated_at;
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

  public Priority getPriority() {
    return priority;
  }

  public void setPriority(int priority) {
    // this.priority = priority;
  }

  @Override
  public String toString() {
    return "Task : \n" + "     id = " + id + "\n     title = " + title + "\n     description = " + description
        + "\n     status = "
        + status
        + "\n     priority = " + priority + "\n     created at = " + created_at + "\n     updated at = " + updated_at
        + " \n";
  }

}
