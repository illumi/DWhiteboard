import java.net.InetAddress;


public class Whiteboard implements Runnable {
	WClient client;
	WWindow window;
	
	public static void main(String[] args) {
		java.awt.EventQueue.invokeLater(new Whiteboard());
		
	}


	public void run() {
		window = new WWindow();

		//new Thread(new WClient();).start();
	}

}
