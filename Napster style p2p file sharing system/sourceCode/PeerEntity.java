import java.io.Serializable;


public class PeerEntity implements Serializable {
	private int port;
	private String ipAddress;
	
	
	public PeerEntity(int port, String ipAddress) {
		super();
		this.port = port;
		this.ipAddress = ipAddress;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	public String getIpAddress() {
		return ipAddress;
	}
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	
	
}
