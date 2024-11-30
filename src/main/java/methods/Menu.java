package methods;

import model.Task;
import service.TaskService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Menu {

  private static Scanner sc = new Scanner(System.in); // static to use without making instances

  private static TaskService taskService = new TaskService();

  // create task and store in the databse server
  public static void createTask() {
    try (Socket socket = new Socket("localhost", 8080);
        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter writer = new PrintWriter(socket.getOutputStream(), true)) {

      // create task
      System.out.print("  Enter title: ");
      String title = sc.nextLine();
      System.out.print("  Enter description: ");
      String description = sc.nextLine();
      System.out.print("  Enter priority (1 = High, 2 = Medium, 3 = Low): ");
      int priority = sc.nextInt();
      sc.nextLine(); // Consume newline

      while (true) {
        if (priority == 1 || priority == 2 || priority == 3) {
          break;
        }
        System.out.println("select between 1-3 for valid priority no!!");
      }

      // create the request body(JSON format)
      String requestBody = String.format("""
          {
              "title": "%s",
              "description": "%s",
              "priority": %d
          }
          """, title, description, priority);

      // send HTTP POST request to create and store a task in the server
      String httpRequest = String.format("""
          POST /tasks HTTP/1.1
          Host: localhost
          Content-Type: application/JSON
          Content-Length: %d

          %s
          """, requestBody.length(), requestBody);

      writer.println(httpRequest);
      writer.flush(); // send the buffer output immediately to the server

      // Read the server response
      String line = null;
      int id = -1; // initialize to -1 cuz if the server's response is null, then the readline()
                   // loop will not execute and hence there will be no id generated, so to use id
                   // later in this program we initialize to -1 or any id which is not present in
                   // the database
      while ((line = reader.readLine()) != null) { // null to if the stream has no data input
        id = Integer.parseInt(line); // returns only id as string
      }

      Task task = taskService.getTaskByID(id);

      if (task != null) {
        // ask user if they want to start the created task
        System.out.println("\nDo you want to start the task (Y/N)");
        String s = sc.nextLine();
        if (s.equals("y") || s.equals("Y")) { // s == "y" is wrong, cuz for non-primitive like string, == operator
                                              // checks if two string references points to the same object in the
                                              // memory or not
          // use == only for primitive type

          // request server to start the task
          String Request = String.format("""
              PUT /startTask/%d/ HTTP/1.1
              Content-Type: text/plain
              """, id);

          writer.println(Request);
          while ((line = reader.readLine()) != null) {
            System.out.println(line);
          }

          return;
        } else if (s.equals("n") || s.equals("N")) {
          return;
        }
        System.out.println("\n  invalid character!!");
        return;
      }

    } catch (IOException e) {
      e.printStackTrace();
    }
    return;
  }

  // Fetch tasks from the server
  public static void FetchTasks() {
    // GET request to the server
    try (Socket socket = new Socket("localhost", 8080);
        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter writer = new PrintWriter(socket.getOutputStream(), true)) {

      StringBuilder request = new StringBuilder();
      request.append("GET /tasks HTTP/1.1\r\n");
      request.append("Host: localhost\r\n");
      request.append("Content-Type: text/plain\r\n\r\n");

      writer.println(request.toString());

      // Read the response from server

      String line = null;
      while ((line = reader.readLine()) != null) {
        System.out.println(line);
      }
    } catch (IOException io) {
      io.printStackTrace();
    }
  }

  // get task by id
  public static void FetchTaskWithId() {
    System.out.print("\n    Enter Task id:");
    int iD = Integer.parseInt(sc.nextLine());
    Task taskId = taskService.getTaskByID(iD);
    if (taskId == null) {
      System.out.println("\n    wrong id!!");
      return;
    }
    // System.out.println("\n" + taskId);

    try (Socket socket = new Socket("localhost", 8080);
        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter writer = new PrintWriter(socket.getOutputStream(), true)) {

      // Construct the GET request with task ID in the URL
      String idRequest = String.format("""
          GET /taskWithId/%d HTTP/1.1
          Host: localhost
          Content-Type: text/plain
          """, iD);

      writer.println(idRequest);
      String line;
      StringBuilder response = new StringBuilder();
      while ((line = reader.readLine()) != null) {
        response.append(line).append("\n");
      }

      // Parse and display the response
      if (response.toString().startsWith("HTTP/1.1 200 OK")) {
        System.out.println("Task found: " + response);
      } else if (response.toString().startsWith("HTTP/1.1 404 Not Found")) {
        System.out.println("No task found with the specified ID.");
      } else {
        System.out.println("Unexpected response: " + response);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  // update task in the database
  public static void updateTask() {
    try (Socket socket = new Socket("localhost", 8080);
        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter writer = new PrintWriter(socket.getOutputStream(), true)) {

      System.out.print("  Enter title: ");
      String title = sc.nextLine();
      System.out.print("  Enter description: ");
      String description = sc.nextLine();
      System.out.print("  Enter priority (1=High, 2=Medium, 3=Low): ");
      int priority = sc.nextInt();
      System.out.print("  Enter id of the task to be updated: ");
      int ID = sc.nextInt();
      sc.nextLine(); // Consume newline

      // create the request body(JSON format)
      String requestBody = String.format("""
          {
              "title": "%s",
              "description": "%s",
              "ID": %d,
              "priority": %d
          }
          """, title, description, ID, priority);

      // send HTTP POST request to create and store a task in the server
      String httpRequest = String.format("""
          POST /taskUpdate HTTP/1.1
          Host: localhost
          Content-Type: application/JSON
          Content-Length: %d

          %s
          """, requestBody.length(), requestBody);

      writer.println(httpRequest);
      writer.flush(); // send the buffer output immediately to the server

      // Read the server response
      String line = null;
      while ((line = reader.readLine()) != null) { // null to if the stream has no data input
        System.out.println(line);
      }

    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  // delecte task
  public static void DeleteTask() {
    try (Socket socket = new Socket("localhost", 8080);
        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter writer = new PrintWriter(socket.getOutputStream(), true)) {
      System.out.println("Enter the ID of the task you want to delete : ");
      int id = Integer.parseInt(sc.nextLine());

      String Request = String.format("""
          DELETE /taskWithId/%d HTTP/1.1
          Host: localhost
          Content-Type: text/plain
          """, id);

      writer.println(Request);
      String line;
      while ((line = reader.readLine()) != null) {
        System.out.println(line);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }

  }

  // start task
  public static void StartTask() {
    try (Socket socket = new Socket("localhost", 8080);
        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter writer = new PrintWriter(socket.getOutputStream(), true)) {
      System.out.println("Enter the ID of the task you want to start : ");
      int id = Integer.parseInt(sc.nextLine());

      String Request = String.format("""
          PUT /startTask/%d HTTP/1.1
          Content-Type: text/plain
          """, id);

      writer.println(Request);
      String line;
      while ((line = reader.readLine()) != null) {
        System.out.println(line);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }

  }

  // complete task
  public static void completeTask() {
    try (Socket socket = new Socket("localhost", 8080);
        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter writer = new PrintWriter(socket.getOutputStream(), true)) {
      System.out.println("Enter the ID of the task you want to complete : ");
      int id = Integer.parseInt(sc.nextLine());

      String Request = String.format("""
          PUT /completeTask/%d HTTP/1.1
          Host: localhost
          Content-Type: text/plain
          """, id);

      writer.println(Request);
      String line;
      while ((line = reader.readLine()) != null) {
        System.out.println(line);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }

  }

  // close program
  public static void close() {
    try (Socket socket = new Socket("localhost", 8080);
        PrintWriter writer = new PrintWriter(socket.getOutputStream(), true)) {
      String Request = String.format("""
          POST /shutdown HTTP/1.1
          Host: localhost
          Content-Type: text/plain
          Content-Length: 0


          """);

      writer.println(Request);
      writer.flush();

    } catch (IOException e) {
      e.printStackTrace();
    }
    System.out.println("Goodbye 😸");
  }
}
