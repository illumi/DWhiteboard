import java.io.*;
import java.net.*;
import java.util.*;

public class DSP_Server implements Runnable {

	private TreeMap<Integer, WrappedUser> users = new TreeMap<Integer, WrappedUser>();;
	private static int id = 0;
	private int defaultPort = 11111;
	private int minPlayers = 2;

	private class WrappedUser extends DSP_User {
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

	private enum ServerState {
		Waiting,
		Ready;
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

		Timer timer = new Timer();
		timer.schedule(new TimerTask () {
			private ServerState state = ServerState.Waiting;

			public void run() {
				System.out.println("in run");

				switch (state) {
				case Waiting:
					if (users.size() >= minPlayers) {
						startRound();
						state = ServerState.Ready;
					}
					break;
				case Ready:
					long currentTime = System.currentTimeMillis();
					if (users.size() < minPlayers) {
						abortRound("To few player, time: " + currentTime);
						state = ServerState.Waiting;
					}

					break;
				default:
					System.err.println(
							"game in unknown state");
				}}}, 1000, 1000);

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

	private synchronized void startRound() {
		System.out.println("Starting round");
		broadcast("Starting round");
	}

	private synchronized void abortRound(String reason) {
		System.out.println("Aborting round");
		broadcast("Aborting round");
	}

	public synchronized void sendChat(DSP_Handler user, String message) {
		Integer id = getPlayerID(user);

		//		NEEDS TO BE CHANGED, YOU DON'T WANT TO SEND CHAT MESSAGE TO YOURSELF

		broadcastExcept(message, user);
	}

	private Integer getPlayerID(DSP_Handler user) {
		System.out.print("In DSP_Server.getPlayerID\n");
		for (Map.Entry<Integer, WrappedUser> entry: users.entrySet()) {
			if (user == entry.getValue().handler) {
				return entry.getKey();
			}
		}
		return null;
	}

	private void broadcast(String message) {
		for (WrappedUser wu : users.values()) {
			wu.handler.sendMessage(message);
		}
	}
	
	private void broadcastExcept(String message, DSP_Handler myself){
		for (WrappedUser wu : users.values()) {
			if(wu.handler != myself)
				wu.handler.sendMessage(message);
		}
	}

	public synchronized DSP_User addUser(DSP_Handler user, String request) {
		System.out.println("In DST_Server.addUser request is: " + request);
		Integer id = getNextID();
		System.out.println("In DST_Server.addUser id is: " + id);
		WrappedUser wu = new WrappedUser(id, user, request);
		System.out.println("In DST_Server.addUser wu.name is: " + wu.name);
		users.put(id, wu);
		System.out.println("In DST_Server.addUser USERS.PUT");	
		System.out.println("User joined: " + request);
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

			System.out.println("Calling user.exit");
			user.exit();

			broadcast("Player left: " + wu.name);

			System.out.println("User left: " + wu.name);}

		catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}

	public static void main(String[] args){

		System.out.println("Starting server.");
		DSP_Server server = new DSP_Server();
		new Thread(server).start();
	}
}

