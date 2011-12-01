import java.rmi.*;
import java.rmi.server.*;
import java.lang.*;
import java.util.*;

/**
*	RMIServer class.
*	Jakub Chlanda
**/
public class RMIServer extends UnicastRemoteObject implements RMIServerInterface {
	
	private boolean vocality = true;
	//list of clients
	private ArrayList<RMINotifyInterface> threads = new ArrayList<RMINotifyInterface>();

	public RMIServer() throws RemoteException {
	}

	//add new client to the server
	public synchronized void join(RMINotifyInterface n) throws RemoteException {
		if(vocality)
			System.out.println("Server.join: " + n.getName());
		threads.add(n);
		broadcastAll(n, "I have joined.\n");
	}

	//send a message to all the clients
	public synchronized void broadcastAll(RMINotifyInterface n, String s) throws RemoteException{
		if(vocality)
			System.out.println("Server.broadcastAll: " + s);
		for(Iterator i = threads.iterator(); i.hasNext();){
			RMINotifyInterface client = (RMINotifyInterface)i.next();
			client.sendMessage(n.getName(),s);
		}
	}

	public synchronized void broadcastTo(RMINotifyInterface n, String s, String username) throws RemoteException{}

	//send a message to all clients apart from the specified one
	public synchronized void broadcastExcept(RMINotifyInterface n, String s, String username) throws RemoteException{
		if(vocality)
			System.out.println("Server.broadcastExcept, message: " + s + " except: " + username);
		for(Iterator i = threads.iterator(); i.hasNext();){
			RMINotifyInterface client = (RMINotifyInterface)i.next();
			if(!client.getName().equals(username))
				client.sendMessage(n.getName(), s);
		}
	}

	//remove a client from the array
	public synchronized void leave(RMINotifyInterface n) throws RemoteException{
		if(vocality)
			System.out.println("Server.leave: " + n.getName());
		threads.remove(n);
		for (Iterator i = threads.iterator();
				i.hasNext();) {
			RMINotifyInterface client = (RMINotifyInterface)i.next();
			client.closeMessage(n.getName());
		}
	}

	//main
	public static void main(String[] args){
		try {
			RMIServer server = new RMIServer();

			Naming.rebind("drawIt", server);
		}
		catch (java.net.MalformedURLException e){
			System.out.println("Malformed URL "	+ e.toString());
		}
		catch (RemoteException e){
			System.out.println("Communication error " +	e.toString());
		}
	}
}
