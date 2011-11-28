import java.io.*;
import java.net.*;

public class DSP_Handler extends Thread {
	final private Socket client;
	final private DSP_Server server;
	final private BufferedReader input;
	final private PrintWriter output; 

	public DSP_Handler(DSP_Server server, Socket client) throws IOException {
		this.server = server;
		this.client = client;

		input = new BufferedReader(new InputStreamReader(client.getInputStream()));
		output = new PrintWriter(client.getOutputStream(), true);
	}

	public void run() {
		try {

			//output.println("In DSP_Handler.run()");

			String message = input.readLine(); 

			User u = server.addUser(this, message);

			//server.sendChat(this, "user id: " + u.id + " user name: " + u.name);

			// Begin handling loop

			while ((message = readMessage()) != null) {
				decodeMessage(message);
			}	

		}
		catch (Exception e) {
			System.err.println(e.getMessage());
		}
		finally {
			try {
				server.removeUser(this);

				output.close();
				input.close();
				client.close();

			}
			catch (IOException e) {
				System.err.println(e.getMessage());
			}
		}
	}

	//get input from terminal
	public String readMessage() {
		try{
			String line = input.readLine().trim();
			if(line.equals(""))
				return null;
			return line;
		}
		catch (IOException e){
			//System.err.println("IO Error"); 
			return null;
		}
	}

	private void decodeMessage(String message) throws Exception {
		if(message.startsWith("chat"))
			server.sendChat(this, message);
		else if(message.startsWith("exit"))
			server.removeUser(this);
		else if (message.startsWith("draw"))
			server.broadcastExcept("draw: " + message, this);
		else
			server.sendChat(this, "Unknow message: " + message);
	}

	public void sendMessage(String message) {
		output.println(message);
	}

	public void exit() throws Exception {
		input.close();
		output.close();
	}

}
