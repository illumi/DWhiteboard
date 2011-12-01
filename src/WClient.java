import java.util.ArrayList;
import java.util.HashMap;

public class WClient implements Runnable {
	private ArrayList<WUser> Users = new ArrayList<WUser>(); //connected users
	private HashMap<WUser, WCanvas> UserBoards = new HashMap<WUser, WCanvas>(); //connected user's boards
	private WCommsInterface c; //link to server
	private Whiteboard w;
	private String name = "User";

	public WClient(Whiteboard w, String how, String where, String name) {
		this.w = w;
		this.name = name;
		
		if(how.equals("SOCKET")) {
			c = new WClientSocket(where);
		}else if(how.equals("RMI")) {
			System.out.println(where + name);
			c = new WClientRMI(where, name);
		} else if(how.equals("X")) {
			//c = new Communication(where, how);
		}
		
		
		sendMessage("user: "+name);
	}

	public void sendMessage(String m) {
		c.sendMessage(m);
	}

	public void run() {
		try {
			while (true) {
				String m = c.readMessage();
				System.out.println(m);
				decodeMessage(m);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			c.close();
		}
		System.exit(0);
	}

	private void decodeMessage(String message) throws Exception {  //TODO implement message system	
		String[] m = message.split(" ");
		
		if(message.startsWith("users"))
			populateUserList(message.split("#"));
		//else if (message.startsWith("draw"))
			
		//else
	}

	private void populateUserList(String[] m) {
		Users.clear();

		for (String s: m) {
			String[] user = s.split(" ");
			Users.add(new WUser(Integer.parseInt(user[0]), user[1]));
		}

		//System.out.println(""+Users.keySet());
		w.populateUserList();
	}
}
