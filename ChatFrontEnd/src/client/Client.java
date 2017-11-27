package client;

import chatfrontend.ChatFrontController;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
//import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
//import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
//import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
//import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

import chatfrontend.ChatFrontController;
import static chatfrontend.LoginController.cf;

public class Client implements Runnable {
	private Socket socket;
	public static String nickname;
        public ChatFrontController controller;
	public static PrintWriter out;
	BufferedReader in;
	BufferedReader stdin;
	String userInput;
	boolean login = true;
        

	public Client(String host, int port, String username, ChatFrontController controller) {	
		try {
			socket = new Socket(host, port);
                        this.nickname = username;
                        this.controller = controller;
			System.out.println("Connected to " + host + " at port " + port);
			out = new PrintWriter(socket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

			new Thread(this).start();

			System.out.print("LOGIN:> ");
			stdin = new BufferedReader(new InputStreamReader(System.in));

			//while((userInput = stdin.readLine()) != null) {
                          while ((userInput = in.readLine()) != null) {
                              //controller.
                              /*
				if (login) {
					setNickname(userInput);
					out.println("LOGIN:> " + userInput);
				} 
                                /*
                                else {
					out.println(nickname + ": " + userInput);
				}
                                */
                                /*
				if (userInput.contains("/upload")) {
					//out.println(nickname + "is uploading a file");
					uploadFile(nickname, userInput);
				}
                              
                                add things to the controller
                                */
                                
			}

		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
        
        public static void send(String msg) {
            out.write(" : " + msg + "\n");
            out.flush();
        }
/*
	public static void main(String host, int port, String username , ChatFrontController cf) {
            
		String host = "0.0.0.0";
		int port = 8080;
		if (args.length < 4) {
		   System.out.println("Now using host=" + host + ", portNumber=" + port);
		} else {
                    
		   host = args[0];
                   String username = args[2];
                   ChatFrontController cf;
                   cf = (ChatFrontController) args[3];
		   port = Integer.valueOf(args[1]).intValue();
		   System.out.println("Now using host=" + host + ", portNumber=" + port);

		}
		
		new Client(host, port, username, cf);
	}
        */


	@Override
	public void run() {
		String serverMessage = null;
		try {
			while ((serverMessage = in.readLine()) != null) {
				System.out.println(serverMessage);
				if (serverMessage.equals("** You are disconnected")) {
					socket.close();
					System.exit(1);
				}
				if (serverMessage.equals("** Error: username invalid")) {
					System.out.print("LOGIN:> ");
				} else {
					login = false;
				}
			}
		} catch (IOException ex) {
			Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
		}
	}


	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public void uploadFile(String nickname, String m) throws IOException {
		
		FileInputStream fis = null;
		try {
			String[] separate = m.split(" ");
			String filename = separate[1];
			File file = new File(filename);
			byte[] byteArray = new byte[(int) file.length()];
			fis = new FileInputStream(file);
			BufferedInputStream bis = new BufferedInputStream(fis);
			
			bis.read(byteArray, 0, byteArray.length);
			OutputStream os = socket.getOutputStream();
			DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(os));
			System.out.println("Sending " + filename + " ...");
			dos.writeLong(file.length());
			dos.write(byteArray, 0, byteArray.length);
			
			dos.flush();
			fis.close();
			bis.close();
		} catch (FileNotFoundException ex) {
			System.out.println("File not found");
		} finally {
			System.out.println("Sent!");
		}	
	}
}
