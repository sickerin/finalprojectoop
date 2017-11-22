import java.io.DataInputStream;
import java.io.PrintStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client implements Runnable {
	
  private static Socket clientSocket = null;
  private static PrintStream os = null;
  private static DataInputStream is = null;
  private static BufferedReader inputLine = null;
  private static boolean closed = false;
  
  public static void main(String[] args) {

    int portNumber = 8080;
    String host = "localhost";

    if (args.length < 2) {
      System.out.println("Usage: java client <host> <portNumber>\n" + "Now using host=" + host + ", portNumber=" + portNumber);
    } else {
      host = args[0];
      portNumber = Integer.valueOf(args[1]).intValue();
    }

    try {
	  clientSocket = new Socket(host, portNumber);
	  inputLine = new BufferedReader(new InputStreamReader(System.in));
	  os = new PrintStream(clientSocket.getOutputStream());
	  is = new DataInputStream(clientSocket.getInputStream());
    } catch (UnknownHostException e) {
      System.err.println("Don't know about host " + host);
    } catch (IOException e) {
      System.err.println("Couldn't get I/O for the connection to the host " + host);
    }

    if (clientSocket != null && os != null && is != null) {
      try {
      	new Thread(new Client()).start();
	    while (!closed) {
	      os.println(inputLine.readLine().trim());
        }
	    os.close();
	   	is.close();
	    clientSocket.close();
      } catch (IOException e) {
      	System.out.println("Unable to connect the client socket!");
      }
    }
  }

  public void run() {

  	String responseLine;

    try {
      while ((responseLine = is.readLine()) != null) {
        System.out.println(responseLine);
       	if (responseLine.indexOf("*** Exiting") != -1)
       	  break;
      	}
      closed = true;
    } catch (IOException e) {
      System.err.println("Uh oh! Something went wrong...");
    }
  }
}