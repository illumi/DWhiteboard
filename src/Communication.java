import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Communication {
	private Socket socket;
	private BufferedReader input;
	private PrintWriter output;
	private String how;
	
	public Communication(String host, String how) {
		this.how = how; //Socket,SOAP,X

		try {
			socket = new Socket(host, 11111);
			output = new PrintWriter(socket.getOutputStream(), true);
			input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		} catch(IOException e) {
			System.out.println("Fail " + e);
			//System.exit(0);
		}
	}
	
	protected synchronized void sendMessage(String message) {
		if (output != null) {
			output.println(message);
		}
	}
	
	protected String readMessage() throws Exception {
		return input.readLine();
	}
	
	protected void close() {
		try {
			socket.close();
			input.close();
			output.close();
		} catch (IOException e ){
			System.err.println("Disastrous");
		}
	}
}
