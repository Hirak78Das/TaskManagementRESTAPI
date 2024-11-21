
import model.Task;
import service.TaskService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class MainClient {

  private static Scanner sc = new Scanner(System.in); // static to use without making instances

  private static TaskService taskService = new TaskService();

  public static void main(String args[]) {

    // try (Socket socket = new Socket("localhost", 8080)) {
    // create output stream to send HTTP request
    // OutputStream output = socket.getOutputStream();
    // PrintWriter writer = new PrintWriter(output, true);
    // BufferedReader reader = new BufferedReader(new
    // InputStreamReader(socket.getInputStream())); // read the input

    // Menu Options
    System.out.println("\n  ~~~~~~~~~~~~~~~~~ TASK MANAGEMENT ~~~~~~~~~~~~~~~~~");
    System.out.println("\n             *              Menu                     *");
    System.out.println("             *       1. Create Task                  *");
    System.out.println("             *       2. View all Tasks               *");
    System.out.println("             *       3. Find task by ID              *");
    System.out.println("             *       4. update Task                  *");
    System.out.println("             *       5. delete Task                  *");
    System.out.println("             *       6. Start Task                   *");
    System.out.println("             *       7. Complete Task                *");
    System.out.println("             *       8. Exit                         *");

    // menu(output, writer, reader);
    menu();
    // } //catch (IOException e) {
    // e.printStackTrace();
    // }
  }

  public static void menu() {

    while (true) {
      System.out.print("\n       choose an option : ");
      String input = sc.nextLine();
      int choice = Integer.parseInt(input);
      switch (choice) {
        case 1:

          try (Socket socket = new Socket("localhost", 8080);
              BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
              PrintWriter writer = new PrintWriter(socket.getOutputStream(), true)) {

            // create task
            System.out.print("  Enter title: ");
            String title = sc.nextLine();
            System.out.print("  Enter description: ");
            String description = sc.nextLine();
            System.out.print("  Enter priority (1=High, 2=Medium, 3=Low): ");
            int priority = sc.nextInt();
            sc.nextLine(); // Consume newline
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
            while ((line = reader.readLine()) != null) { // null to if the stream has no data input
              System.out.println(line);
            }

            Task task = taskService.createTask(title, description, priority); //

            // ask user if they want to start the created task
            System.out.println("\nDo you want to start the task (Y/N)");
            String s = sc.nextLine();
            if (s.equals("y") || s.equals("Y")) { // s == "y" is wrong, cuz for non-primitive like string, == operator
                                                  // checks if two string references points to the same object in the
                                                  // memory or not
              // use == only for primitive type
              started(task.getId());
              break;
            } else if (s.equals("n") || s.equals("N")) {
              break;
            }
            System.out.println("\n  invalid character!!");
            break;

          } catch (IOException e) {
            e.printStackTrace();
          }

        case 2:
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

          break;

        case 3:
          // get task with specified id from server
          System.out.print("\n    Enter Task id:");
          int iD = Integer.parseInt(sc.nextLine());
          Task taskId = taskService.getTaskByID(iD);
          if (taskId == null) {
            System.out.println("\n    wrong id!!");
            break;
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
          break;

        case 4:
          // update task
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

          // boolean updated = taskService.updateTask(ID, titl, des, prior);
          // System.out.println("\nUpdate Task 1 : " + (updated ? "Success" : "Failure"));
          break;
        case 5:
          // delete task
          System.out.println("Enter task id to delete: ");
          boolean deleted = taskService.deleteTask(sc.nextInt());
          sc.nextLine(); // consume newline present in the console buffer
          System.out.println("\nDeleted Task 2 : " + (deleted ? "Success" : "Failure"));

          break;
        case 6:
          // start task
          System.out.print("\n   Enter task id : ");
          started(sc.nextInt());
          sc.nextLine(); // consume newline
          break;
        case 7:
          // complete task
          System.out.println("\n  Enter task id : ");
          completed(Integer.parseInt(sc.nextLine()));
          break;
        case 8:
          return;

        default:
          System.out.println("\n         please enter no from 1 - 6 !! ");
          break;
      }
    }
  }

  public static void started(int id) {
    boolean started = taskService.startTask(id);
    if (started) {
      System.out.println("\n       Task id : " + id + "started");
      return;
    }
    System.out.println("\n  wrong id. Please enter valid id to start the task. choose from the menu");
  }

  public static void completed(int id) {
    boolean completed = taskService.completeTask(id);
    if (completed) {
      System.out.println("\n       Task id : " + id + "completed");
      return;
    }
    System.out.println("\n  wrong id. Please enter valid id to complete the task. choose from the menu");

  }

}
