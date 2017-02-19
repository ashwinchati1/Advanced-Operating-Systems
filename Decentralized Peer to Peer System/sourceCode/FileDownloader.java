import java.io.BufferedInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Properties;

/**
 * 
 */

/**
 * @author ashwin
 *
 */
public class FileDownloader implements Runnable{

	Socket client;
	public FileDownloader(Socket socket){
		client = socket;
	}

	@Override
	public void run() {
		ObjectInputStream ois;
		try {
			ois = new ObjectInputStream(client.getInputStream());
			String fname = (String) ois.readObject();
			System.out.println("FileName received: " + fname);
			Properties props = new Properties();
			props.load(new FileInputStream("config.properties"));
			String clientFolderLocation = props.getProperty("clientFolderLocation");
						
			

			FileInputStream stream = null;
			BufferedInputStream bufferedInputStream = null;
			OutputStream outputStream = null;
			int bytesRead=0;
			int size = 0;
			
			try {

					System.out.println("Waiting...");
					try {
					System.out.println("Accepted connection : " + client);
						// send file
					System.out.println("File sending started");
					
					byte[] bytes = new byte[1024];
						
					File file = new File(clientFolderLocation+"/"+fname);	
					FileInputStream bis = new FileInputStream(file);	
					BufferedInputStream in = new BufferedInputStream(bis);
					DataOutputStream out = new DataOutputStream(client.getOutputStream());
					
					out.writeLong(file.length());
									
					
					int bytesToSend = (int) file.length();
					
										
					int  byteSent = 0;	
					int i=0;
						
					while(byteSent < bytesToSend){
						
						int bytesThisTime = bytesToSend - byteSent;
						
						bytesThisTime = bytesThisTime < bytes.length ? bytesThisTime : bytes.length;
						
						in.read(bytes, 0, (int) bytesThisTime);
						
						out.write(bytes,0,bytesThisTime);
						
						out.flush();
						
						byteSent += bytesThisTime;
						i++;
						

					}
						
											
	                System.out.println("File Sent successfully");
						
					}catch(FileNotFoundException exception){
						System.out.println("File does not exists");
					}
					finally {
						if (bufferedInputStream != null)
							bufferedInputStream.close();
						if (outputStream != null)
							outputStream.close();
						if (client != null)
							client.close();
					}
				
			} catch (IOException e) {
				
				e.printStackTrace();
			}	
			
			
		} catch (IOException e) {
			
			
		} catch (ClassNotFoundException e) {
			
			
		} 
	}
}
