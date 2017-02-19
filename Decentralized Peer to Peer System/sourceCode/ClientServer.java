import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 
 */

/**
 * @author ashwin
 *
 */
public class ClientServer implements Runnable {
	int port;
	public ClientServer(int listenPort){
		port = listenPort;
	}

	@Override
	public void run() {
		try {
		
			ServerSocket serverSocket = new ServerSocket(port);
		
			do
			{
				Socket socket = serverSocket.accept();
				System.out.println("Connection accepted for download");
				Thread proxy=new Thread(new FileDownloader(socket));

				proxy.start();
			}while (true);
			
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}
}
