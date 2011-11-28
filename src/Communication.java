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
	private boolean connected = false;
	private final Integer port = 11111;

	public Communication(String host, String how) {
		this.how = how; //Socket,SOAP,X

		if (how.equals("SOCKET")) {
			connected = sockConnect(host);
		} else if (how.equals("SOAP")) {
			connected = soapConnect(host);
		} else if (how.equals("RMI")) {
			connected = rmiConnect(host);
		} else {
			System.err.println("Eek! Unknown communication method?");
			System.exit(1);
		}
	}

	private boolean sockConnect(String host) {
		try {
			socket = new Socket(host, port);
			output = new PrintWriter(socket.getOutputStream(), true);
			input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			return true;
		} catch(IOException e) {
			System.out.println("Fail " + e);
			return false;
		}
	}

	private boolean soapConnect(String host) {return true;} //TODO implement SOAP connection

	private boolean rmiConnect(String host) {return true;} //TODO implement rmi connection

	protected synchronized void sendMessage(String message) {
		if (connected) {
			output.println(message);
		}
	}

	protected String readMessage() throws Exception { //TODO implement RMI and SOAP read depending on 'how' String above
		return input.readLine();
	}

	protected void close() { //TODO implement RMI and SOAP close depending on 'how' String above
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
