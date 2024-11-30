package methods;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import model.Task;
import service.TaskService;

public class Requests {

  private static TaskService taskService = new TaskService();

  // Handle delete task
  public static void handleDeleteTask(PrintWriter writer, int id) {
    if (taskService.deleteTask(id)) {
      // Respond to the client
      String response = """
          HTTP/1.1 200 OK
          Content-Type: text/plain
          Content-Length: 0

          """;
      System.out.println(response);
      writer.println("\n        task deletion successful : ");
      writer.flush();
    } else {
      writer.println("\n        no task found with id " + id);
      writer.flush();
    }
  }

  // Handle Update task
  public static void handleUpdateTask(PrintWriter writer, BufferedReader reader) {
    // Read the headers(line-by-line) to get the lenght of request body
    try {
      String line;
      int contentlength = 0;
      while (!(line = reader.readLine()).isEmpty()) { // BufferedReader readLine() reads line by line i.e inside loop
        if (line.startsWith("Content-Length:")) {
          contentlength = Integer.parseInt(line.split(":")[1].trim());
        }
      }

      // Read the body
      char[] body = new char[contentlength];
      reader.read(body, 0, contentlength); // body is the destination where the data will be stored after reading each
                                           // char
      String requestbody = new String(body); // convert to string to parse to task object

      // parse the requestbody from JSON to object
      boolean updated = ParseRequests.parseTaskUpdate(requestbody);

      System.out.println("\nUpdate Task : " + (updated ? "Updated successfully" : "Failure"));

      // Respond to the client
      String response = """
          HTTP/1.1 200 OK
          Content-Type: text/plain
          Content-Length: 0

          """;
      System.out.println(response);
      writer.println("\ntask updated successfully : ");
      writer.flush();

    } catch (IOException io) {
      io.printStackTrace();
    }

  }

  // Handle Task with ID with Get request
  public static void handleGetIdTask(PrintWriter out, int id) {
    // Find the task with the specified ID

    Task foundTask = taskService.getTaskByID(id);
    if (foundTask != null) {
      // Task found, send it back as the response
      String response = String.format("""
          HTTP/1.1 200 OK
          Content-Type: application/json
          Content-Length: %d,

          %s
          """,
          foundTask.toString().length(), foundTask);
      System.out.println(response);
      out.println(response);
    } else {
      // Task not found
      String response = """
          HTTP/1.1 404 Not Found
          Content-Type: text/plain
          Content-Length: 0

          """;
      System.out.println(response);
      out.println(response);
    }
    out.flush();
  }

  // Handle POST /tasks
  public static void handlePostTasks(PrintWriter out, BufferedReader in) {
    // Read the headers(line-by-line) to find the "Content-Length:"

    try {
      String line;
      int contentlength = 0;
      while (!(line = in.readLine()).isEmpty()) { // BufferedReader readLine() reads line by line i.e inside loop
        if (line.startsWith("Content-Length:")) {
          contentlength = Integer.parseInt(line.split(":")[1].trim());
        }
      }

      // Read the body
      char[] body = new char[contentlength];
      in.read(body, 0, contentlength); // body is the destination where the data will be stored after reading each char
      String requestbody = new String(body); // convert to string to parse to task object

      // parse the requestbody from JSON to object
      Task task = ParseRequests.parseTask(requestbody);
      // tasks.add(task);

      // show in the server
      System.out.println("\n     New Task with title '" + task.getTitle() + "' added to the database server ");

      // Respond to the client
      out.println(task.getId());
      out.flush();
    } catch (IOException e) {
      e.printStackTrace();
      System.out.println("Received POST /tasks request");
    }
  }

  // Handle Get /tasks
  public static void handleGetTasks(PrintWriter out) { // we dont to catch PrintWriter exception as it is unchecked
    StringBuilder response = new StringBuilder();
    response.append("HTTP/1.1 200 OK\r\n");
    response.append("Content-Type: text/plain\r\n\r\n");

    if (taskService.getAllTasks().isEmpty()) {
      response.append("No tasks available.\n");
    } else {
      response.append("      ⭕ ⭕ ⭕ ⭕ ⭕ ⭕ ⭕ ⭕ ⭕ Tasks ⭕ ⭕ ⭕ ⭕ ⭕ ⭕ ⭕ ⭕ ⭕ \n\n");
      for (Task task : taskService.getAllTasks()) {
        response.append(task).append("\n");
      }
      response.append("      ⭕ ⭕ ⭕ ⭕ ⭕ ⭕ ⭕ ⭕ ⭕ ⭕ ⭕ ⭕ ⭕ ⭕ ⭕ ⭕ ⭕ ⭕ ⭕ ⭕ \n ");
    }
    out.println(response.toString());
    out.flush();
  }

  // close server when client exits
  public static void closeServer(Socket socket) throws IOException {
    System.out.println("\n\n        Received shutdown request, shutting down the server...");
    socket.close(); // Stop accepting new connections
    System.exit(0); // Exit the application or process
  }

  // Handle start task
  public static void handleStartTask(PrintWriter writer, int id) {
    int completed = taskService.startTask(id);
    if (completed == 1) {
      writer.println("\n       Task with id " + id + " has started 😃");
      writer.flush();
    } else if (completed == 2) {
      writer.println("\n       Task with id " + id + " has already completed ");
      writer.flush();
    } else {
      writer.println("\n ❌ wrong id. Please enter valid id to complete the task. choose from the menu");
      writer.flush();
    }
  }

  // Handle complete Task
  public static void handleCompleteTask(PrintWriter writer, int id) {
    int completed = taskService.completeTask(id);
    if (completed == 1) {
      writer.println("\n       Task with id " + id + " is completed 👍");
      writer.flush();
    } else if (completed == 2) {
      writer.println(
          "\n       Task with id " + id + " hasn't started yet 😥 Press 6 and enter the id again to start the task!!");
      writer.flush();
    } else if (completed == 3) {
      writer.println("\n       Task with id " + id + " has already completed ");
      writer.flush();
    } else {
      writer.println("\n ❌ wrong id. Please enter valid id to complete the task. choose from the menu");
      writer.flush();
    }
  }

}
