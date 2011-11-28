import java.awt.Color;
import java.awt.Component;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Enumeration;
import javax.swing.*;

public class WClient implements Runnable {
	private ArrayList<User> Users = new ArrayList<User>();
	protected Communication c;
	
	public WClient() {
		/*PaneDrawing.setVisible(false);
		PaneLogin.setVisible(true);*/
	}
	
	public void run() {
		try {
			while (true) {
				//String m = c.readMessage();
				//handleMessage(m);
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
