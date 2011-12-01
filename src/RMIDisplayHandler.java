import java.rmi.*;
import java.rmi.server.*;
import java.io.*;

/**
*   RMIDisplayHandler.
*   Middle layer betwee server and client.
*   Jakub Chlanda
**/
public class RMIDisplayHandler extends UnicastRemoteObject implements RMINotifyInterface {

	private PrintStream textArea;
	private String name;

    public RMIDisplayHandler(PrintStream textArea) throws RemoteException{
    	    this.textArea = textArea;
    }

    //server calls this method it wants to send message to a client
    public void sendMessage(String name, String message) throws RemoteException{
        try {
    	    textArea.append(name + " says: " + message + "\n");
    	}
        catch(Exception e){
            System.out.println("Message Failure.\n");
            e.printStackTrace();
        };
    }
    
    //inform client that someone has left
    public void closeMessage(String name) throws RemoteException{
        try {
    	    textArea.append(name + " has left.\n");
    	}
        catch(Exception e){
            System.out.println("Message Failure.\n");
        };
    }
    
    //tel server your name
    public String getName() {
    	return name;
    }
    
    public void setName(String name){
    	this.name = name;
    }
}