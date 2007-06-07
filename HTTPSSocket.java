
public class HTTPSSocket extends TPSocket {


	public void disconnect() {
		// TODO Auto-generated method stub

	}

	public boolean isConnected() {
		// TODO Auto-generated method stub
		return false;
	}

	public int recvBody(int len, String data) {
		// TODO Auto-generated method stub
		return 0;
	}


	public int recvHeader(int len, String data) {
		// TODO Auto-generated method stub
		return 0;
	}


	public void send(String header, int hlen, String data, int len) {
		// TODO Auto-generated method stub

	}
	private String host;
	private int port;
	public void setServerAddress(String host, int port) {
	    this.host = host;
	    this.port = port;
	}
}
