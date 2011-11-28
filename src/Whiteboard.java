public class Whiteboard implements Runnable {
	private WClient client;
	private WWindow window;

	public static void main(String[] args) {
		java.awt.EventQueue.invokeLater(new Whiteboard());
	}

	public void sendMessage(String m) {
		client.sendMessage(m);
	}

	public void login(String how, String where, String name) {
		client = new WClient(this, how, where, name);
		Runnable r = client;
		new Thread(r);
	}

	public void run() {
		window = new WWindow(this);
	}
}
