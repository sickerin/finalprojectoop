package server;

/*
 * 
 */

//import java.io.BufferedOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
//import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
//import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
//import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import channel.Channel;
import user.User;


public class Server {
  public static ArrayList<Channel> channelList = new ArrayList<Channel>();
  private ServerSocket ss;
  public static int slots = 0;
  Channel defaultChannel;

  public class ClientHandler implements Runnable {
    SimpleDateFormat sdf = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss Z");
    Socket clientSocket;
    User user;
    Channel userChannel;
    BufferedReader in;

    public ClientHandler(Socket s, Channel ch) {
      try {
        clientSocket = s;
        userChannel = ch;
        user = new User(clientSocket, defaultChannel.getName());
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
      } catch (Exception ex) {
        ex.printStackTrace();
      }
    }

    @Override
    public void run() {
      String message = null;
      String nickname = null;
      Date date = new Date();
      String today = sdf.format(date);

      try {
        while((message = in.readLine()) != null) {
          if (message.contains("LOGIN:>") && user.isLogin()) {
            nickname = message.substring(8, message.length());
            if (isValid(user, nickname)) {
              user.setUsername(nickname);
              userChannel.addUser(user);
              notifyUserChannel(user);
              notifyAllUsers(user);
              System.out.println(nickname + " login from " +
                      clientSocket.getLocalSocketAddress() + " @ " + today);
              slots++;
            } else {
              notifyUser(user, null, 0);
              user.setLogin(true);
            }
          } else if (message.contains(": /tell")) {
            commandTell(user.getCurrentChannel(), message);
            notifyUserChannel(user);
          } else if (message.contains(": /allusers")) {
            commandListAll(user);
            notifyUserChannel(user);
          } else if (message.contains(": /users")) {
            commandList(user, message);
            notifyUserChannel(user);
          } else if (message.contains(": /join")) {
            commandJoin(user, message);
            notifyUserChannel(user);
          } else if (message.contains(": /channels")) {
            commandChannels(user);
            notifyUserChannel(user);
          } else if (message.contains(": /upload")) {
            commandUpload(user, message);
            notifyUserChannel(user);
          } else if (message.contains(": /quit")) {
            commandQuit(user.getCurrentChannel(), user);
          } else if (!message.contains("LOGIN:>")) {
            sendAllInChannel(user.getCurrentChannel(), user, message);
            notifyUserChannel(user);
          }
        }
      } catch (Exception ex) {
        ex.printStackTrace();
      } finally {
        Date d = new Date();
        String now = sdf.format(d);
        Iterator<Channel> channelIt = channelList.iterator();
        Channel ch = null;
        while (channelIt.hasNext()) {
          ch = (Channel) channelIt.next();
          if (ch.getName().equals(user.getCurrentChannel())) {
            ch.removeUser(user.getUsername());
            System.out.println(user.getUsername() + " logout @ " + now);
            break;
          }
        }
        slots--;
        if ((ch.showNumberOfUsers() == 0) &&
            !ch.getName().equals("default")) {
          channelList.remove(ch);
          System.out.println(ch.getName() + " destroyed by " +
              user.getUsername() + " @ " + today);
        }
      }
    }

    public boolean isValid(User u, String name) {
      Iterator<Channel> channelIt = channelList.iterator();
      Channel ch = null;
      String s = null;

      if (name.contains(" "))
        return false;

      while (channelIt.hasNext()) {
        ch = (Channel) channelIt.next();
        Set<String> keys = ch.getAllUsers().keySet();
        Iterator<String> userIt = keys.iterator();
        while (userIt.hasNext()) {
          s = (String) userIt.next();
          if (s.equals(name))
            return false;
        }
      }
      
      u.setLogin(false);
      return true;
    }

    public void commandTell(String channelName, String m) {
      String[] separate = m.split(" ");
      String sender = separate[0].substring(0,separate[0].length()-1);
      String receiver = separate[2];
      String message = sender + " tells you: ";
      boolean found = false;

      for(int i=3; i<separate.length; i++)
        message += separate[i] + " ";

      Iterator<Channel> channelIt = channelList.iterator();
      Channel ch = null;
      User u = null;
      PrintWriter out = null;

      while (channelIt.hasNext()) {
        ch = (Channel) channelIt.next();
        if (ch.getName().equals(channelName)) {
          if ((u = ch.getUser(receiver)) != null) {
            out = u.getUserOutputStream();
            out.println(message + "\n#" + u.getCurrentChannel() + ":> ");
            out.flush();
            found = true;
            break;
          }
        }
      }

      if (!found) {
        while (channelIt.hasNext()) {
          ch = (Channel) channelIt.next();
          if (ch.getName().equals(channelName)) {
            User s = ch.getUser(sender);
            out = s.getUserOutputStream();
            out.println("Warning:" + receiver + " is not in channel!" +
                      "\n#" + u.getCurrentChannel() + ":> ");
            out.flush();
            break;
          }
        }
      }
    }

    public void commandListAll(User u) {
      Iterator<Channel> channelIt = channelList.iterator();
      PrintWriter out = null;
      Channel ch = null;

      while (channelIt.hasNext()) {
        ch = (Channel) channelIt.next();
        out = u.getUserOutputStream();
        out.println(ch.listUsersInChannel());
        out.flush();
      }
    }

    public void commandList(User u, String m) {
      String[] separate = m.split(" ");
      if (separate.length < 2) {
        return;
      }
      String channelName = separate[2];
      Iterator<Channel> channelIt = channelList.iterator();
      PrintWriter out = null;
      Channel ch = null;
      boolean isChPresent = false;

      while (channelIt.hasNext()) {
        ch = (Channel) channelIt.next();
        if (ch.getName().equals(channelName)) {
          out = u.getUserOutputStream();
          out.println(ch.listUsersInChannel());
          out.flush();
          isChPresent = true;
          break;
        }
      }

      if (!isChPresent) {
        notifyUser(user, channelName, 3);
      }

    }

    public void commandJoin(User u, String m) {
      String[] separate = m.split(" ");
      String channelName = separate[2];
      Channel ch = null;
      Channel chIn = null;
      Channel newCh = null;
      String leavingCh = null;
      Date date = new Date();
      String today = sdf.format(date);
      boolean isChPresent = false;

      ArrayList<Channel> channelListReplica =
                  new ArrayList<Channel>(channelList);

      Iterator<Channel> channelIt = channelListReplica.iterator();
      while (channelIt.hasNext()) {
        ch = (Channel) channelIt.next();
        if (ch.getName().equals(channelName)) {
          leavingCh = u.getCurrentChannel();
          Iterator<Channel> it = channelList.iterator();
          while (it.hasNext()) {
            chIn = (Channel) it.next();
            if (chIn.getName().equals(leavingCh)) {
              chIn.removeUser(u.getUsername());
            } else if (chIn.getName().equals(channelName)) {
              chIn.addUser(u);
            }
          }
          u.setCurrentChannel(channelName);
          notifyUser(user, channelName, 4);
          isChPresent = true;
          break;
        }
      }

      if (!isChPresent) {
        newCh = new Channel(channelName);
        new File(channelName).mkdir();
        channelList.add(newCh);
        newCh.addUser(u);
        ch.removeUser(u.getUsername());
        u.setCurrentChannel(newCh.getName());
        notifyUser(user, channelName, 4);
        System.out.println("Channel " + newCh.getName() +
            " is created by " + u.getUsername() + " @ " + today);
      }
    }

    public void commandChannels(User u) {
      Iterator<Channel> channelIt = channelList.iterator();
      PrintWriter out = null;
      Channel ch = null;

      while (channelIt.hasNext()) {
        ch = (Channel) channelIt.next();
        out = u.getUserOutputStream();
        out.println("** " + ch.getName() + " (" + ch.showNumberOfUsers() + " users)");
        out.flush();
      }
    }

    public void commandUpload(User u, String m) {
      PrintWriter out = u.getUserOutputStream();
      String[] separate = m.split(" ");
      if (separate.length < 2) {
        //out.println("Failed to send file");
        return;
      }
      String filename = separate[2];
      
      //System.out.println("Uploading " + filename);

      receiveFile(u.getCurrentChannel(), filename);
    }

    public void commandQuit(String channelName, User u) {
      Iterator<Channel> channelIt = channelList.iterator();
      PrintWriter out = null;
      Channel ch = null;
      Date date = new Date();
      String today = sdf.format(date);

      synchronized(channelList) {
        while (channelIt.hasNext()) {
          ch = (Channel) channelIt.next();
          if (ch.getName().equals(channelName)) {
            ch.removeUser(u.getUsername());
          }
          out = u.getUserOutputStream();
          out.println("** You are disconnected");
          out.flush();
          break;
        }
      }
      if ((ch.showNumberOfUsers() == 0) &&
          !ch.getName().equals("default")) {
        channelList.remove(ch);
        System.out.println(ch.getName() + " destroyed by " +
        u.getUsername() + " @ " + today);
      }
      System.out.println(u.getUsername() + " logout @ " + today);
    }

    public void receiveFile(String channelName, String filename) {  
      try {
        FileOutputStream fos = null;
        int bytesRead;
        int current = 0;
        byte[] byteArray = new byte[6022386];
        InputStream is = clientSocket.getInputStream();
        BufferedInputStream bis = new BufferedInputStream(is);
        DataInputStream dis = new DataInputStream(bis);
        long fileSize = dis.readLong();
        
        fos = new FileOutputStream(filename);
        BufferedOutputStream bos = new BufferedOutputStream(fos);
        bytesRead = 0;
        
        while (fileSize > 0 && ((bytesRead = dis.read(byteArray, 0, (int)Math.min(byteArray.length, fileSize))) != -1)) {
          bos.write(byteArray, 0, bytesRead);
          fileSize -= bytesRead;
        }
      
        bos.write(byteArray, 0, current);
        fos.flush();
        bos.flush();
        fos.close();
        bos.close();
        
      } catch (IOException ex) {
        Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
      } 
    }
  }
  
  public static void main(String[] args) {
    int port = 8080;
    if (args.length < 1) {
      System.out.println("Usage: java server <portNumber>\n" + "Now using port number=" + port);
      } else {
          port = Integer.valueOf(args[0]).intValue();
      }

    try {
      new Server().listen(port);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }


  private void listen(int port) throws IOException {
    Socket clientSocket = null;

    try {
      ss = new ServerSocket(port);
      System.out.println("Server is listening on: " +
          ss.getLocalSocketAddress());
      defaultChannel = new Channel("default");
      new File("default").mkdir();
      channelList.add(defaultChannel);

      while(true) {
        clientSocket = ss.accept();
        Thread t = new Thread(new ClientHandler(clientSocket, defaultChannel));
        t.start();
      }
      
    } catch (Exception ex) {
      ex.printStackTrace();
    }
    
  }

  public void notifyAllUsers(User u) {
    Iterator<Channel> channelIt = channelList.iterator();
    PrintWriter out = null;
    User usr = null;
    Channel ch = null;

    while (channelIt.hasNext()) {
      ch = (Channel) channelIt.next();
      Set<String> keys = ch.getAllUsers().keySet();
      synchronized (this) {
        for (Iterator<String> i = keys.iterator(); i.hasNext();) {
          usr = ch.getUser((String) i.next());
          try {
            if (!usr.getUsername().equals(u.getUsername())) {
              out = usr.getUserOutputStream();
              out.println("** User " + u.getUsername() +
                  " joined #" + ch.getName() + " channel\n" +
                  "#" + usr.getCurrentChannel() + ":> ");
              out.flush();
            }
          } catch (Exception e1) {
            e1.printStackTrace();
          }
        }
      }
    }
  }

  public void sendAllInChannel(String channelName, User u, String message) {
    Iterator<Channel> channelIt = channelList.iterator();
    HashMap<String, User> users = null;
    PrintWriter out = null;
    Channel ch = null;
    User usr = null;

    while (channelIt.hasNext()) {
      ch = (Channel) channelIt.next();
      if (ch.getName().equals(channelName)) {
        users = ch.getAllUsers();
        Set<String> keys = users.keySet();
        synchronized (this) {
          for (Iterator<String> i = keys.iterator(); i.hasNext();) {
            usr = ch.getUser((String) i.next());
            if (!usr.getUsername().equals(u.getUsername())) {
              out = usr.getUserOutputStream();
              out.println(message + "\n#" +
                    usr.getCurrentChannel() + ":> ");
              out.flush();
            }
          }
        }
      }
    } 
  }

  public void notifyUser(User u, String s, int i) {
    PrintWriter out = u.getUserOutputStream();
    switch(i) {
      case 0:
        out.println("** Error: username invalid");
        out.flush();
        break;
      case 1:
        out.println("** All messages from " + s + " will be discarded");
        out.flush();
        break;
      case 2:
        out.println("** All messages from " + s + " will be shown");
        out.flush();
        break;
      case 3:
        out.println("** Channel " + s + " does not exist");
        out.flush();
        break;
      case 4:
        out.println("** " + u.getUsername() + " joined " + s);
        out.flush();
        break;
    }
  }

  public void notifyUserChannel(User u) {
    PrintWriter out = u.getUserOutputStream();
    try {
      out.println("#" + u.getCurrentChannel() + ":> ");
      out.flush();
    } catch (Exception e1) {
      e1.printStackTrace();
    }
  }

}
