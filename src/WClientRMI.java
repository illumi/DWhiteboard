import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.rmi.Naming;
import java.rmi.Remote;

public class WClientRMI implements Communication {
	protected boolean connected = false;
	private BufferedReader input; 
	private PrintStream output; 
	RMINotifyInterface displayChat;
	RMIServerInterface server;

	WClientRMI(String host) {
		try {
			input = new BufferedReader(new InputStreamReader(System.in)); 
			output = System.out;
			Remote remoteObject = Naming.lookup("rmi://" + host + "/drawIt");
			
			if (remoteObject instanceof RMIServerInterface) {
				server = (RMIServerInterface)remoteObject ;
				displayChat = new RMIDisplayHandler(output);
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
		if(connected) {
			System.out.println(message);
		}
	}

	public String readMessage() {
		try{
			String line = input.readLine().trim();
			if(line.equals(""))
				return null;
			return line;
		}
		catch (IOException e){
			System.err.println("IO Error"); 
			return null;
		}
	}	

	public void close() {
		try {
			connected = false;
			output.close();
			input.close();
		} catch (IOException e){
			System.err.println("Disastrous");
		}
	}

}
