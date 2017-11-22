package users;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;


public class User {
	private Socket userSocket;
	private String name;
	private String currentChannel;
	private PrintWriter out;
	private boolean login = true;

	public User(Socket socket, String channel) throws IOException {
		this.userSocket = socket;
		this.currentChannel = channel;
		//this.out = new PrintWriter(socket.getOutputStream());
	}

	public void setUsername(String name) {
		this.name = name;
	}

	public void setLogin(boolean b) {
		this.login = b;
	}

	public boolean isLogin() {
		return this.login;
	}

	public String getUsername() {
		return this.name;
	}

	public String getCurrentChannel() {
		return this.currentChannel;
	}

	public void setCurrentChannel(String channel) {
		this.currentChannel = channel;
	}

	/*
	public PrintWriter getUserOutputStream() {
		return this.out;
	}
	*/

}
