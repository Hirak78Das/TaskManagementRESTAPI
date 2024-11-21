package httpServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import model.Task;
import service.TaskService;

import java.util.List;

// no requirement for client socket, it is created automatically by curl or browser

public class Server {
  private ServerSocket server = null; // listens for http port
  private Socket socket = null; // for sending and recieving data (via streams)
  private static List<Task> tasks = new ArrayList<>(); // In-memory task
  // storage
  private static TaskService taskService = new TaskService();

  public Server(int port) {
    try {
      server = new ServerSocket(port); // acts as a gatekeeper
      System.out.println("\nserver started  on port " + port);

      System.out.println("waiting fo a client"); // here the client is browser/curl not any custom clientsocket

      while (true) {
        socket = server.accept(); // handover the communication to Socket by ServerSocket
        System.out.println("client accepted");

        // handle the http request
        handleRequest(socket);
      }
    } catch (IOException io) {
      System.out.println("\nproblem with specified port " + port + ". " + io.getMessage());
      io.printStackTrace();
    }
  }

  // handle requests and respond to client
  public static void handleRequest(Socket socket) {
    try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

      // Read the http request
      String requestLine = in.readLine(); // reads only the first line of htrp request i.e. methods and path
      if (requestLine == null || requestLine.isEmpty()) {
        System.out.println("not valid requests. might be empty http request");
        out.println("invalid request");
        return;
      }
      System.out.println("recieved request: " + requestLine);

      // Parse http methods and path
      String[] requestParts = requestLine.split(" ");
      String method = requestParts[0];
      String path = requestParts[1];

      if (method.equals("GET") && path.equals("/tasks")) {
        handleGetTasks(out);
      } else if (method.equals("POST") && path.equals("/tasks")) {
        handlePostTasks(out, in);
      } else if (method.equals("GET") && path.startsWith("/taskWithId/")) {
        int id = Integer.parseInt(path.split("/taskWithId/")[1]);

        handleGetIdTask(out, id);
      } else if (method.equals("POST") && path.equals("/taskUpdate")) {
        handleUpdateTask(out, in);
      } else {
        // Handle unknown or invalid requests
        String response = "HTTP/1.1 404 Not Found\r\n" +
            "Content-Type: text/plain\r\n\r\n" + // requires a line between header and body therefore used \r\n\r\n
            "wrong requests/page not found..\n";
        out.println(response);
      }
    } catch (IOException io) {
      io.printStackTrace();
    } finally {
      try {
        socket.close(); // Ensure the socket is closed
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  // Handle Update task
  private static void handleUpdateTask(PrintWriter writer, BufferedReader reader) {
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
      boolean updated = parseTaskUpdate(requestbody);

      System.out.println("\nUpdate Task 1 : " + (updated ? "Updated successfully" : "Failure"));

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
  private static void handleGetIdTask(PrintWriter out, int id) {
    // Find the task with the specified ID
    Task foundTask = null;

    for (Task task : tasks) {
      if (task.getId() == id) {
        foundTask = task;
        break; // Stop searching once the task is found
      }
    }

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
  private static void handlePostTasks(PrintWriter out, BufferedReader in) {
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
      Task task = parseTask(requestbody);
      tasks.add(task);

      // show in the server
      System.out.println("\n     New Task with title : " + task.getTitle() + " added to the server ");

      // Respond to the client
      String response = """
          HTTP/1.1 200 OK
          Content-Type: text/plain
          Content-Length: 0

          """;
      out.println(response);
      out.flush();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  // Method to parse JSON and create a Task object for handlePostTasks() method
  private static Task parseTask(String json) {
    // Assuming simple JSON format: {"title": "...", "description": "...",
    // "priority": "..."}
    String title = json.split("\"title\": \"")[1].split("\"")[0];
    String description = json.split("\"description\": \"")[1].split("\"")[0];
    int priority = Integer.parseInt(json.split("\"priority\": ")[1].split("}")[0].trim());
    return taskService.createTask(title, description, priority);
  }

  // Method to parse JSON and to update the task object
  private static boolean parseTaskUpdate(String json) {
    // Assuming simple JSON format: {"title": "...", "description": "...",
    // "priority": "..."}
    String title = json.split("\"title\": \"")[1].split("\"")[0];
    String description = json.split("\"description\": \"")[1].split("\"")[0];
    int id = Integer.parseInt(json.split("\"ID\": ")[1].split(",")[0].trim());
    int priority = Integer.parseInt(json.split("\"priority\": ")[1].split("}")[0].trim());
    if (taskService.updateTask(id, title, description, priority)) {
      return true;
    }
    return false;
  }

  // Handle Get /tasks
  private static void handleGetTasks(PrintWriter out) { // we dont to catch PrintWriter exception as it is unchecked
    StringBuilder response = new StringBuilder();
    response.append("HTTP/1.1 200 OK\r\n");
    response.append("Content-Type: text/plain\r\n\r\n");

    if (tasks.isEmpty()) {
      response.append("No tasks available.\n");
    } else {
      response.append("Tasks :\n");
      for (Task task : tasks) {
        response.append(task).append("\n");
      }
    }
    out.println(response.toString());
    out.flush();
  }

  public static void main(String args[]) {
    Server server = new Server(8080);
  }
}
