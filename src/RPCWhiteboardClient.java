/*
 * Automatically generated by jrpcgen 1.0.7 on 30/11/11 21:48
 * jrpcgen is part of the "Remote Tea" ONC/RPC package for Java
 * See http://remotetea.sourceforge.net for details
 */
import org.acplt.oncrpc.*;
import java.io.IOException;

import java.net.InetAddress;

/**
 * The class <code>WhiteboardClient</code> implements the client stub proxy
 * for the WHITEBOARD_PROG remote program. It provides method stubs
 * which, when called, in turn call the appropriate remote method (procedure).
 */
public class RPCWhiteboardClient extends OncRpcClientStub {

    /**
     * Constructs a <code>WhiteboardClient</code> client stub proxy object
     * from which the WHITEBOARD_PROG remote program can be accessed.
     * @param host Internet address of host where to contact the remote program.
     * @param protocol {@link org.acplt.oncrpc.OncRpcProtocols Protocol} to be
     *   used for ONC/RPC calls.
     * @throws OncRpcException if an ONC/RPC error occurs.
     * @throws IOException if an I/O error occurs.
     */
    public RPCWhiteboardClient(InetAddress host, int protocol)
           throws OncRpcException, IOException {
        super(host, RPCWhiteboard.WHITEBOARD_PROG, 1, 0, protocol);
    }

    /**
     * Constructs a <code>WhiteboardClient</code> client stub proxy object
     * from which the WHITEBOARD_PROG remote program can be accessed.
     * @param host Internet address of host where to contact the remote program.
     * @param port Port number at host where the remote program can be reached.
     * @param protocol {@link org.acplt.oncrpc.OncRpcProtocols Protocol} to be
     *   used for ONC/RPC calls.
     * @throws OncRpcException if an ONC/RPC error occurs.
     * @throws IOException if an I/O error occurs.
     */
    public RPCWhiteboardClient(InetAddress host, int port, int protocol)
           throws OncRpcException, IOException {
        super(host, RPCWhiteboard.WHITEBOARD_PROG, 1, port, protocol);
    }

    /**
     * Constructs a <code>WhiteboardClient</code> client stub proxy object
     * from which the WHITEBOARD_PROG remote program can be accessed.
     * @param client ONC/RPC client connection object implementing a particular
     *   protocol.
     * @throws OncRpcException if an ONC/RPC error occurs.
     * @throws IOException if an I/O error occurs.
     */
    public RPCWhiteboardClient(OncRpcClient client)
           throws OncRpcException, IOException {
        super(client);
    }

    /**
     * Constructs a <code>WhiteboardClient</code> client stub proxy object
     * from which the WHITEBOARD_PROG remote program can be accessed.
     * @param host Internet address of host where to contact the remote program.
     * @param program Remote program number.
     * @param version Remote program version number.
     * @param protocol {@link org.acplt.oncrpc.OncRpcProtocols Protocol} to be
     *   used for ONC/RPC calls.
     * @throws OncRpcException if an ONC/RPC error occurs.
     * @throws IOException if an I/O error occurs.
     */
    public RPCWhiteboardClient(InetAddress host, int program, int version, int protocol)
           throws OncRpcException, IOException {
        super(host, program, version, 0, protocol);
    }

    /**
     * Constructs a <code>WhiteboardClient</code> client stub proxy object
     * from which the WHITEBOARD_PROG remote program can be accessed.
     * @param host Internet address of host where to contact the remote program.
     * @param program Remote program number.
     * @param version Remote program version number.
     * @param port Port number at host where the remote program can be reached.
     * @param protocol {@link org.acplt.oncrpc.OncRpcProtocols Protocol} to be
     *   used for ONC/RPC calls.
     * @throws OncRpcException if an ONC/RPC error occurs.
     * @throws IOException if an I/O error occurs.
     */
    public RPCWhiteboardClient(InetAddress host, int program, int version, int port, int protocol)
           throws OncRpcException, IOException {
        super(host, program, version, port, protocol);
    }

    /**
     * Call remote procedure sendMessage_1.
     * @param arg1 parameter (of type String) to the remote procedure call.
     * @throws OncRpcException if an ONC/RPC error occurs.
     * @throws IOException if an I/O error occurs.
     */
    public void sendMessage_1(String arg1)
           throws OncRpcException, IOException {
        XdrString args$ = new XdrString(arg1);
        XdrVoid result$ = XdrVoid.XDR_VOID;
        client.call(RPCWhiteboard.sendMessage_1, RPCWhiteboard.WHITEBOARD_VERS, args$, result$);
    }

    /**
     * Call remote procedure readMessage_1.
     * @return Result from remote procedure call (of type String).
     * @throws OncRpcException if an ONC/RPC error occurs.
     * @throws IOException if an I/O error occurs.
     */
    public String readMessage_1()
           throws OncRpcException, IOException {
        XdrVoid args$ = XdrVoid.XDR_VOID;
        XdrString result$ = new XdrString();
        client.call(RPCWhiteboard.readMessage_1, RPCWhiteboard.WHITEBOARD_VERS, args$, result$);
        return result$.stringValue();
    }

}
// End of WhiteboardClient.java
