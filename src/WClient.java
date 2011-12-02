import java.util.ArrayList;
import java.util.HashMap;

public class WClient implements Runnable {
	private ArrayList<WUser> Users = new ArrayList<WUser>(); //connected users
	private HashMap<Integer, WCanvas> UserBoards = new HashMap<Integer, WCanvas>(); //connected user's boards
	private WCommsInterface c; //link to server
	private Whiteboard w;
	private String name = "User";

	public WClient(Whiteboard w, String how, String where, String name) {
		this.w = w;
		this.name = name;

		if(how.equals("SOCKET")) {
			c = new WClientSocket(where, name);
		}else if(how.equals("RMI")) {
			c = new WClientRMI(where, name);
		} else if(how.equals("RPC")) {
			c = new RPCWhiteboardClientRun(where, name);
		}
	}

	public void sendMessage(String m) {
		c.sendMessage(m);
	}

	public void run() {
		try {
			while (true) {
				String m = c.readMessage();
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
		
		if(message.startsWith("users")) { //users split by hash, id and username split by space
			populateUserList(message.split("#"));
		} //else if (message.startsWith("draw")) // draw <id> <x> <y>
			//drawOn(Integer.parseInt(m[1]), Double.parseDouble(m[2]), Double.parseDouble(m[3]));
		//else
	}
	
	private void drawOn(Integer id, Double x, Double y) {
		//WCanvas canvas = UserBoards.get(id); //Draw on users board
		//canvas.passiveDraw(x, y);
		//UserBoards.put(id, canvas);
	}

	private void populateUserList(String[] m) {
		Users.clear();
		
		for (int i = 1; i < m.length; i++){
			String[] user = m[i].split(" ");
			Users.add(new WUser(Integer.parseInt(user[0]), user[1]));
		}

		w.populateUserList(Users);

		//RePopulate the UserBoards ignoring existing users + deleting old users
		for (WUser u: Users) {
			if (!UserBoards.containsKey(u.getClass())) {
				UserBoards.put(u.getId(), new WCanvas(null));
			}
		}
	}
	
	public void logout() {
		sendMessage("exit");
		c.close();
	}
}
