package channels;

import java.util.*;

import users.User;

public class Channel {
	private String name;
	private HashMap<String, User> users;

	public Channel(String name) {
		this.name = name;
		this.users = new HashMap<String, User>();
=	}

	public String getName() {
		return this.name;
	}

	public void addUser(User user) {
		users.put(user.getUsername(), user);
	}

	public void removeUser(String nickname) { 
		users.remove(nickname);
	}

	public User getUser(String username) {
		if (this.users.containsKey(username))
			return this.users.get(username);
		return null;
	}

	public HashMap<String, User> getAllUsers() {
		return this.users;
	}

	public int showNumberOfUsers() {
		return users.size();
	}
	
	public String listUsersInChannel() {
		Set keys = this.users.keySet();
		Iterator it = keys.iterator();
		String all = "";

		while(it.hasNext()) {
			User u = this.users.get(it.next());
			all += "** " + u.getUsername() + " in " + this.name + "\n";
		}
		return all.substring(0, all.length()-1);
	}

}