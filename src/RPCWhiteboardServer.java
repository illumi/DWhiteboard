import org.acplt.oncrpc.*;
import org.acplt.oncrpc.server.*;
import java.io.IOException;

public class RPCWhiteboardServer extends RPCWhiteboardServerStub {
	String message;
	
	public RPCWhiteboardServer() throws OncRpcException, IOException {
	
	}
	
	public void sendMessage_1(String message) {
		this.message = message;
		System.out.println("Received message: " + message);
	}
	
	public String readMessage_1() {
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