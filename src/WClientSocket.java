import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class WClientSocket implements WCommsInterface {
	private Socket socket;
	private BufferedReader input;
	private PrintWriter output;
	protected boolean connected = false;
	protected final Integer port = 11111;
	
	WClientSocket(String host) {
		try {
			socket = new Socket(host, port);
			output = new PrintWriter(socket.getOutputStream(), true);
			input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			connected = true;
		} catch(IOException e) {
			System.out.println("Fail " + e);
		}
	}
	
	public synchronized void sendMessage(String message) {
		if (connected) {
			output.println(message);
		}
	}
	
	public synchronized String readMessage() throws Exception {
		return input.readLine();
	}

	public void close() {
		try {
			connected = false;
			socket.close();
			input.close();
			output.close();
		} catch (IOException e){
			System.err.println("Disastrous");
		}
	}
	
}
