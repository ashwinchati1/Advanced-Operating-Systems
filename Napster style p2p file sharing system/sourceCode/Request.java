
public class Request {
	private int port;
	private String ip, request, fileName;
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getRequest() {
		return request;
	}
	public void setRequest(String request) {
		this.request = request;
	}
	
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public Request(int port, String ip, String request, String file) {
		super();
		this.port = port;
		this.ip = ip;
		this.request = request;
		this.fileName  = file;
	}
	@Override
	public String toString() {
		return "Request [port=" + port + ", ip=" + ip + ", request=" + request
				+ ", fileName=" + fileName + "]";
	}
	
	
	
	
}
