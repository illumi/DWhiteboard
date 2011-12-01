public interface WCommsInterface {

	public void sendMessage(String message);
	public String readMessage() throws Exception;
	public void close();
	
}
