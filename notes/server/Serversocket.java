
/* ~~~~~~~~~~~~~~~~~~~SERVER PROGRAM~~~~~~~~~~~~~~~~~~~~~~~~~
  
To make a server application two sockets are needed.
   1. A ServerSocket which waits/accepts for the client requests(when a client makes a new socket)
   2. A plain old socket to use for communication(getOutputStream()) with the client.

Communication : getOutputStream() is responsible to send the output( converts input data to structured binary) through the socket

Close the connection :
  After finishing the data exchange, explicitly close teh socket as well as input/output streams.
*/

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket; // accepts client requests to connect on the specified port by the client
import java.net.Socket; // send and recieve data(streams)

public class Serversocket {
  private Socket socket = null;
  private ServerSocket server = null;
  private DataInputStream in = null;

  // start the server by calling this constructor
  public Serversocket(int port) {

    // starts server and waits for a connection
    try {
      server = new ServerSocket(port);
      System.out.println("\nserver started");

      System.out.println("\nwaiting for a client ..."); // listening for clientsocket at the specified port

      socket = server.accept();
      System.out.println("Client accepted");

      // take the input which is sen by the client socket
      in = new DataInputStream(new BufferedInputStream(socket.getInputStream()));

      String line = "";

      // reads message from client until "Over" is sent
      while (!line.equals("Over")) {
        // need to check for error if network error
        try {
          line = in.readUTF();
          System.out.println(line);
        } catch (Throwable e) {
          System.out.println(e);
        }
      }
      System.out.println("Closing connection ...");

      // close connection
      socket.close();
      in.close();
    } catch (IOException io) {
      System.out.println(io);
    }
  }

  public static void main(String args[]) {
    Serversocket server = new Serversocket(5000);
  }
}
