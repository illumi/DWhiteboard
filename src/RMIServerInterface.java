import java.rmi.*;

public interface RMIServerInterface extends Remote {
    public void join(RMINotifyInterface n) throws RemoteException;
    
    public void broadcastAll(RMINotifyInterface n, String message) throws RemoteException;
    
    public void broadcastTo(RMINotifyInterface n, String message, String to) throws RemoteException;

    public void broadcastExcept(RMINotifyInterface n, String s, String except) throws RemoteException;

    public void leave(RMINotifyInterface n) throws RemoteException;
}