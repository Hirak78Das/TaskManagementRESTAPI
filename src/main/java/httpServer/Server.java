package httpServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import methods.Requests;

// no requirement for client socket, it is created automatically by curl or browser

public class Server {
  private ServerSocket server = null; // listens for http port
  private Socket socket = null; // for sending and recieving data (via streams)
  // private static List<Task> tasks = new ArrayList<>(); // used In-memory task
  // storage before using any database

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
        Requests.handleGetTasks(out);
      } else if (method.equals("POST") && path.equals("/tasks")) {
        Requests.handlePostTasks(out, in);
      } else if (method.equals("GET") && path.startsWith("/taskWithId/")) {
        int id = Integer.parseInt(path.split("/taskWithId/")[1]);

        Requests.handleGetIdTask(out, id);
      } else if (method.equals("POST") && path.equals("/taskUpdate")) {
        Requests.handleUpdateTask(out, in);
      } else if (method.equals("DELETE") && path.startsWith("/taskWithId/")) {
        int id = Integer.parseInt(path.split("/taskWithId/")[1]);
        Requests.handleDeleteTask(out, id);
      } else if (method.equals("PUT") && path.startsWith("/startTask/")) {
        int id = Integer.parseInt(path.split("/startTask/")[1]);
        Requests.handleStartTask(out, id);
      } else if (method.equals("PUT") && path.startsWith("/completeTask/")) {
        int id = Integer.parseInt(path.split("/completeTask/")[1]);
        Requests.handleCompleteTask(out, id);
      } else if (method.equals("POST") && path.equals("/shutdown")) {
        Requests.closeServer(socket);
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

  public static void main(String args[]) {
    Server server = new Server(8080);
  }
}
