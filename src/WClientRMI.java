import java.rmi.*;
import java.rmi.server.*;
import java.io.*;

public class WClientRMI implements Communication {
	protected boolean connected = false;
	private BufferedReader input; 
	private PrintStream output; 
	RMINotifyInterface displayChat;
	RMIServerInterface server;

	WClientRMI(String host, String username) {
		try {
			input = new BufferedReader(new InputStreamReader(System.in)); 
			output = System.out;
			Remote remoteObject = Naming.lookup("rmi://" + host + "/drawIt");
			
			if (remoteObject instanceof RMIServerInterface) {
				server = (RMIServerInterface)remoteObject ;
				displayChat = new RMIDisplayHandler(output);
				
				//
				displayChat.setName(username);
				server.join(displayChat);
				//
				connected = true;
			}
			else{
				System.out.println("I didn't find the RMI server I was looking for.");
				System.exit(0);
			}
		} catch(Exception e) {
			System.out.println("Fail " + e);
		}
	}
	
	public void sendMessage(String message) {
		try{
			if(connected) {
				server.broadcastAll(displayChat, message);
			}
		}
		catch (RemoteException re){
			System.out.println("Fail " + re);
		}
	}

	public String readMessage() {
		try{
			String line = input.readLine().trim();
			if(line.equals(""))
				return null;
			return line;
		}
		catch (Exception ie) {
			output.append("Failed to send message.");
			return null;
		}
		
	}	

	public void close() {
		try {
			server.leave(displayChat);
			
			input.close();
			output.close();
			
			System.exit(0);
			
		} catch (IOException e){
			System.err.println("Disastrous");
		}
	}

}
