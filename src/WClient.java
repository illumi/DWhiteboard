import java.util.ArrayList;

public class WClient implements Runnable {
	private ArrayList<User> Users = new ArrayList<User>();
	private Communication c;
	private Whiteboard w;
	private String nick = "User";

	public WClient(Whiteboard w, String how, String where, String name) {
		this.w = w;
		this.nick = name;
		c = new Communication(where, how);
	}

	public void sendMessage(String m) {
		c.sendMessage(m);
	}

	public void run() {
		try {
			while (true) {
				String m = c.readMessage();
				//decodeMessage(m); //TODO implement message system
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
