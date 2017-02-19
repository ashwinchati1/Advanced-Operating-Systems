import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class PeerServer implements Runnable {
	
	int port;

	public PeerServer(int incPort) {
		
		port = incPort;
			
		
	}

	@Override
	public void run() {
		try {
			ServerSocket serverSocket = new ServerSocket(port);
			
			do
			{
				Socket socket = serverSocket.accept();
				
				Thread proxy = new Thread(new HashServer(socket));

				proxy.start();
			
							
			}while (true);
			
		} catch (IOException e) {
			
			//e.printStackTrace();
		}
		
	}

}
