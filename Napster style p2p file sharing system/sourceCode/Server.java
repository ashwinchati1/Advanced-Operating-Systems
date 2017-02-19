import java.io.*;
import java.net.*;
import java.util.Properties;

public class Server {

	private static ServerSocket serverSocket;
	private static int PORT ;
	
	public static void main(String[] args) throws IOException
	{
		Properties props = new Properties();
		props.load(new FileInputStream("config.properties"));
		PORT = Integer.parseInt(props.getProperty("serverPort"));
		
		try
		{
			System.out.println("Server is up and running");
			serverSocket = new ServerSocket(PORT);
		}
		catch (IOException e)
		{
			System.out.println("\nUnable to set up port!");
			System.exit(1);
		}
	
		do
		{
			Socket socket = serverSocket.accept();

			System.out.println("\n Enter your choice .\n");

			//Create a thread to handle communication with client
					
			Thread proxy=new Thread(new ProxyServer(socket));

			proxy.start();
		}while (true);
	}
}
	
	
	
