import java.io.*;
import java.net.*;
import java.util.*;

public class DSP_Server implements Runnable {

	private TreeMap<Integer, WrappedUser> users = new TreeMap<Integer, WrappedUser>();;
	private int id = 0;
	private int defaultPort = 11111;

	private class WrappedUser extends User {
		public DSP_Handler handler;

		public WrappedUser(int id, DSP_Handler handler, String name) {
			super(id, name);
			this.handler = handler;
		}	
	}

	public DSP_Server() {}

	private int getNextID(){
		return id++;
	}

	public void run() {
		ServerSocket sock;

		try {
			sock = new ServerSocket(defaultPort);
			System.out.println("Server started.");
		}
		catch (IOException e) {
			System.err.println("Cannot open port " + defaultPort);
			return;
		}
		while (true) {
			try {
				System.out.println("New client arrived.");
				Socket client = sock.accept();

				DSP_Handler handler = 
						new DSP_Handler(this, client);

				new Thread(handler).start();

			}
			catch (Exception e) {
				System.err.println(e.getMessage());
			}
		}
	}

	public synchronized void sendChat(DSP_Handler user, String message) {
		Integer id = getPlayerID(user);
		broadcastExcept(message, user);
	}

	private Integer getPlayerID(DSP_Handler user) {
		for (Map.Entry<Integer, WrappedUser> entry: users.entrySet()) {
			if (user == entry.getValue().handler) {
				return entry.getKey();
			}
		}
		return null;
	}

	public void broadcast(String message) {
		for (WrappedUser wu : users.values()) {
			wu.handler.sendMessage(message);
			System.out.println("Broadcast is: " + message);
		}
	}

	public void broadcastExcept(String message, DSP_Handler myself){
		for (WrappedUser wu : users.values()) {
			if(wu.handler != myself)
				wu.handler.sendMessage(message);
			else
				System.out.println(wu.getName() + " said: " + message);
		}
	}

	public synchronized User addUser(DSP_Handler user, String request) {
		System.out.println("DST_Server.addUser");
		Integer id = getNextID();
		System.out.println("id is: " + id);
		WrappedUser wu = new WrappedUser(id, user, request);
		System.out.println("wu.name is: " + wu.name);
		users.put(id, wu);
		broadcast("User joined: " + request);

		return wu;
	}

	public synchronized void removeUser(DSP_Handler user){
		// must ignore requests to remove non-registered users
		try{
			Integer id = getPlayerID(user);

			if (id == null) return;

			WrappedUser wu = users.remove(id);

			if (wu == null) return;

			user.exit();

			broadcast("User left: " + wu.name);
		}

		catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}

	public static void main(String[] args){
		System.out.println("Starting server.");
		new Thread(new DSP_Server()).start();
	}
}

