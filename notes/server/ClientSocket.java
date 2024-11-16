
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

// ------------Client-side program~~~~~~~~~~~~~~~~~~~~~~~~~
// In this program, the client keeps reading input from a user and sends it to the server until "Over" is typed.
// To communicate over a socket connection, streams are used to both input and output the data

public class ClientSocket {

  // initialize socket and input output streams to null. null meaning when the
  // program progress, it will refer to an object
  private Socket socket = null;
  private DataInputStream input = null;
  private DataOutputStream output = null;

  // constructor to put ip address and port on socket
  public ClientSocket(String address, int port) { // ip address and http port
    try {
      // Socket constructor throws IOException, hence we need to handle this checked
      // exception
      socket = new Socket(address, port);
      System.out.println("\nConnected");

      // takes input from terminal
      input = new DataInputStream(System.in);

      // sends output to the socket --> send output in the form of structured binary
      // data from server-client or client-server
      output = new DataOutputStream(socket.getOutputStream());

    } catch (UnknownHostException u) { // subclass of IOException, hence it can be thrown automatically by IOException,
                                       // but to know I'm using this UnknownHostException
      System.out.println(u); // throws when unknown IP address/domain
      return;
    } catch (IOException io) { // IOException can throw any exception of its subclass like UnknownHostException
                               // here
      System.out.println(io); // throws when port is unavailabe or server not responding or network timeouts
      return;
    }

    // String to read message from input
    String line = "";

    // keep reading until "Over" is input
    while (!line.equals("Over")) {
      try {
        // line = input.readLine(); --> depricated from jdk 11
        // use BufferedReader to read input string
        BufferedReader buffer = new BufferedReader(new InputStreamReader(System.in));
        line = buffer.readLine();
        output.writeUTF(line); // sends string data in structured binary data to server socket
      } catch (IOException i) { // if the input is not string
        System.out.println(i);
      }
    }
    // close the connection
    try {
      input.close();
      output.close();
      socket.close();
    } catch (IOException i) {
      System.out.println(i);
    }
  }

  public static void main(String args[]) {

    // open socket
    ClientSocket client = new ClientSocket("127.0.0.1", 5000);
    // server ip address and tcp port

    // used ip address of localhost(virtual server which connects with your machine)
    // TCP port 5000 is used for testing, communicating between local network
    // devices
    // TCP port 80 for HTTP
  }
}
