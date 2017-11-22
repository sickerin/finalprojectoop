import java.io.DataInputStream;
import java.io.PrintStream;
import java.io.IOException;
import java.net.Socket;
import java.net.ServerSocket;

public class Server {

  private static ServerSocket serverSocket = null;
  private static Socket clientSocket = null;
  private static final int maxClientsCount = 50;
  private static final clientThread[] threads = new clientThread[maxClientsCount];

  public static void main(String args[]) {

  	int portNumber = 8080;

    if (args.length < 1) {
    	System.out.println("Usage: java server <portNumber>\n" + "Now using port number=" + portNumber);
    } else {
      portNumber = Integer.valueOf(args[0]).intValue();
    }

    try {
      serverSocket = new ServerSocket(portNumber);
    } catch (IOException e) {
    	System.out.println("Something went wrong setting up server socket. Please try again later.");
      System.exit(1);
    }

    while (true) {
    	try {
      	clientSocket = serverSocket.accept();
      	int i = 0;
      	for (i = 0; i < maxClientsCount; i++) {
      		if (threads[i] == null) {
        		(threads[i] = new clientThread(clientSocket, threads)).start();
        		break;
      		}
      	}
      	if (i == maxClientsCount) {
        		PrintStream os = new PrintStream(clientSocket.getOutputStream());
        		os.println("Server overload. Please try again later.");
        		os.close();
        		clientSocket.close();
      	}
    	} catch (IOException e) {
    		System.out.println("Something went wrong establishing clientSocket");
    	}
    }
  }
}

class clientThread extends Thread {

  private String clientName = null;
  private DataInputStream is = null;
  private PrintStream os = null;
  private Socket clientSocket = null;
  private final clientThread[] threads;
  private int maxClientsCount;

  public clientThread(Socket clientSocket, clientThread[] threads) {
  	this.clientSocket = clientSocket;
  	this.threads = threads;
  	maxClientsCount = threads.length;
  }

  public void run() {
    int maxClientsCount = this.maxClientsCount;
    clientThread[] threads = this.threads;
    
    try {
      is = new DataInputStream(clientSocket.getInputStream());
      os = new PrintStream(clientSocket.getOutputStream());
    	String name = "";
  		while (true) {
     		os.println("Enter your username.");
    		name = is.readLine().trim();        		
        if (name != "") { break; }		
    	}

    	os.println("Welcome " + name + "\n");
      os.println("To send meme, do /meme <filepath>. To quit, do /quit");
    	synchronized (this) {
      	for (int i = 0; i < maxClientsCount; i++) {
        	if (threads[i] != null && threads[i] == this) {
          	clientName = "@" + name;
          	break;
        	}
      	}
    	}

    	while (true) {
        boolean image = false;
      	String line = is.readLine();
      	if (line.startsWith("/quit")) {
        		break;
      	}

        if (line.startsWith("/meme")) {
          image = true;
        }

        if (image) {
          //image is being sent
          synchronized (this) {
            for (int i = 0; i < maxClientsCount; i++) {
              if (threads[i] != null && threads[i].clientName != null) {
                threads[i].os.println("<" + name + "> " + "sent a meme.");
              }
            }
          }
        } else { 
          //text message is being sent
          synchronized (this) {
            for (int i = 0; i < maxClientsCount; i++) {
              if (threads[i] != null && threads[i].clientName != null) {
                threads[i].os.println("<" + name + "> " + line);
              }
            }
          }
        }
        
        
      }
    
      synchronized (this) {
        for (int i = 0; i < maxClientsCount; i++) {
          if (threads[i] != null && threads[i] != this && threads[i].clientName != null) {
        		threads[i].os.println("*** The user " + name + " left ***");
          }
        }
      }

      os.println("*** Exiting ***");

      synchronized (this) {
        for (int i = 0; i < maxClientsCount; i++) {
          if (threads[i] == this) {
            threads[i] = null;
          	break;
          }
        }
      }
    
      is.close();
      os.close();
      clientSocket.close();
      System.exit(0);
    } catch (IOException e) {
      System.out.println("Uh oh! Something went wrong.");
    }
  }
}
