import java.rmi.*;
import java.rmi.server.*;
import java.io.*; 

/**
*	RMIClient class.
*	Jakub Chlanda
**/
public class RMIClient{

	private String message = "";
	private boolean firstMessage = true;
	private static String name;
	private static String hostname;
	private BufferedReader input; 
	private PrintStream output; 
	private boolean constructed;

	//middle layer
	RMINotifyInterface dH;
	//server
	RMIServerInterface server;

	//use this constructor when run from command line
	public RMIClient(){
		input = new BufferedReader(new InputStreamReader(System.in)); 
		output = System.out;
		constructed = false;
	}
	
	//use this constructor wiht gui
	public RMIClient(String name, String hostname){
		
		this.name = name;
		this.hostname = hostname;
		
		input = new BufferedReader(new InputStreamReader(System.in)); 
		output = System.out;
		constructed = true;
	}

	//start me
	public void run(){
		try {
			if(!constructed){
				System.out.print("hostname: ");
				hostname = input.readLine();
			}
			
			//find server 
			Remote remoteObject = Naming.lookup("rmi://" + hostname + "/drawIt");

			if (remoteObject instanceof RMIServerInterface) {
				server = (RMIServerInterface)remoteObject ;
				dH = new RMIDisplayHandler(output);
			}
			else{
				System.out.println("I didn't find the RMI server I was looking for.");
				System.exit(0);
			}
		}
		catch(Exception e){
			System.out.println("RMI Lookup Exception");
			System.exit(0);
		};    

		try{
			String message = "";
			if(constructed)
				firstMessage = false;
			else
				System.out.print("username: ");

			//if everything ok start main excecutin loop - read message and handle it
			while((message = readMessage()) != null){
				handleMessage(message);
			}	
		}
		catch (Exception e) {
			System.err.println(e.getMessage());
		}
		finally {
			try {

				server.leave(dH);

				output.close();
				input.close();
			}
			catch (IOException e) {
				System.err.println(e.getMessage());
			}
		}
	}

	//only with command line
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

	//act upon message 
	private void handleMessage(String message) {
		try{
			if (firstMessage) {
				name = message;
				dH.setName(message);
				server.join(dH);
				firstMessage = false;
			}
			else {
				if(message.startsWith("chat:")){
					message = message.substring(5, message.length());
					server.broadcastExcept(dH, message, name);
				}
				else if(message.trim().equals("exit")){
					server.leave(dH);
					
					input.close();
					output.close();
					
					System.exit(0);
				}
				else
					server.broadcastAll(dH, message);
			}
		}
		catch (Exception ie) {
			output.append("Failed to send message.");
		}

	}


	public static void main(String args[]) {
		new RMIClient().run();
	}

}