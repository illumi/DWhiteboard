import org.acplt.oncrpc.*;
import java.net.InetAddress;
import java.io.IOException;

public class RPCWhiteboardClientRun implements WCommsInterface{
	private String clientName;
	private RPCWhiteboardClient client;
	
	public RPCWhiteboardClientRun(String serverHostName, String clientName) {
		this.clientName = clientName;
		try {
			client = new RPCWhiteboardClient(InetAddress.getByName(serverHostName), OncRpcProtocols.ONCRPC_UDP);
			client.getClient();
			sendMessage(clientName);
		} catch (Exception e) {
			System.err.println("Error occured while attempting to establish a connection with ONC RPC server: " + e.getMessage());
		}
	}
	
	public String getClientName() {
		return clientName;
	}
	
	public void sendMessage(String message) {
		try {
			client.sendMessage_1(message);
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		
	}
	
	public String readMessage() {
		String message = new String("");
		try {
			message = client.readMessage_1();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return message;
	}
	
	public void close() {
		try {
			sendMessage("User " + getClientName() + " has left.");
			client.close();
		}catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}
	
}