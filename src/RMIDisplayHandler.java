import java.rmi.*;
import java.rmi.server.*;
import java.io.*;

public class RMIDisplayHandler extends UnicastRemoteObject implements RMINotifyInterface {

	private PrintStream textArea;
	private String name;

    public RMIDisplayHandler(PrintStream textArea) throws RemoteException{
    	    this.textArea = textArea;
    }
    
    public void arriveMessage(String name) throws RemoteException{
        try {
    	    textArea.append(name + " has joined\n");
    	}
        catch(Exception e){
            System.out.println("Message Failure.\n");
            e.printStackTrace();
        };
    }
    
    public void sendMessage(String name, String message) throws RemoteException{
        try {
    	    textArea.append(name + " says: " + message + "\n");
    	}
        catch(Exception e){
            System.out.println("Message Failure.\n");
            e.printStackTrace();
        };
    }
    
    public void closeMessage(String name) throws RemoteException{
        try {
    	    textArea.append(name + " has left.\n");
    	}
        catch(Exception e){
            System.out.println("Message Failure.\n");
        };
    }
    
    public String getName() {
    	return name;
    }
    
    public void setName(String name){
    	this.name = name;
    }
}