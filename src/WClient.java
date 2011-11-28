import java.util.HashMap;

public class WClient implements Runnable {
	private HashMap<Integer, User> Users = new HashMap<Integer, User>(); //connected users
	private Communication c; //link to server
	private Whiteboard w;
	private String name = "User";

	public WClient(Whiteboard w, String how, String where, String name) {
		this.w = w;
		this.name = name;
		c = new Communication(where, how);
		sendMessage("user: "+name);
	}

	public void sendMessage(String m) {
		c.sendMessage(m);
	}

	public void run() {
		try {
			while (true) {
				String m = c.readMessage();
				//decodeMessage(m); //TODO implement message system
				System.out.println(m);
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

	/*private void populateUserList() {
		UserItemList = new JList(Users.toArray());
		UserItemList.setCellRenderer(new UserCellRenderer());
	}*/
}
