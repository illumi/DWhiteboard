import java.rmi.*;

public interface RMINotifyInterface extends Remote {

	public void arriveMessage(String name) throws RemoteException;
	
    public void sendMessage(String name, String message) throws RemoteException;
    
    public void closeMessage(String name) throws RemoteException;
    
    public String getName() throws RemoteException;
    
    public void setName(String s) throws RemoteException;
}