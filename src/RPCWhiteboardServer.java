import org.acplt.oncrpc.*;
import org.acplt.oncrpc.server.*;
import java.io.IOException;
import java.util.HashMap;

public class RPCWhiteboardServer extends RPCWhiteboardServerStub {
	private String message;
	private HashMap<Integer, RPCWhiteboardClientRun> users;
		
	public RPCWhiteboardServer() throws OncRpcException, IOException {
	}
	
	
	public void sendMessage_1(String message) {
		System.out.println("Server receives a message: " + message);
		this.message = message;
		
	}
	
	public String readMessage_1() {
		System.out.println("Server has received a message: " + message);
		return message;
	}
	
	public void close_1() {
		
	}
	
	public static void main(String[] args) {
		try { 
			RPCWhiteboardServer server = new RPCWhiteboardServer();
			System.out.println("ONC/RPC server has started");
			server.run();
		} catch ( Exception e ) {
			e.printStackTrace(System.out);
		}
	}
}