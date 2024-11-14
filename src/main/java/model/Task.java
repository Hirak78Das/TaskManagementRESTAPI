package model;

public class Task {
  private int id; // unique id for each task
  private String title; // short title of the task
  private String description;
  private String status; // E.g. "pending", "In progress", "completed"
  private int priority; // E.g. 1 = High, 2 = Medium, 3 = Low

  public Task(int id, String title, String description, String status, int priority) {
    this.id = id;
    this.title = title;
    this.description = description;
    this.status = status;
    this.priority = priority;
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

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
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
